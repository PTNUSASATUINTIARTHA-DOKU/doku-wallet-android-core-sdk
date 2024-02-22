package com.dokuwallet.verificationpagesdk.ui.liveness

import android.media.MediaActionSound
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dokuwallet.coresdk.data.network.parameter.vpparameter.CaptureIdReq
import com.dokuwallet.coresdk.utils.CommonUtils
import com.dokuwallet.verificationpagesdk.R
import com.dokuwallet.verificationpagesdk.VpSDKBaseFragment
import com.dokuwallet.verificationpagesdk.databinding.VpSdkFragmentLivenessBinding
import com.dokuwallet.verificationpagesdk.enums.ResponseCode
import com.dokuwallet.verificationpagesdk.enums.VerificationType
import com.dokuwallet.verificationpagesdk.utils.CamHelper
import com.dokuwallet.verificationpagesdk.utils.PermissionHelper
import com.dokuwallet.verificationpagesdk.utils.TextViewCountDownTimer
import com.dokuwallet.verificationpagesdk.utils.VPUtils
import com.dokuwallet.verificationpagesdk.utils.VerificationPage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.nio.ByteBuffer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

internal class LivenessFragment : VpSDKBaseFragment<VpSdkFragmentLivenessBinding>(
    VpSdkFragmentLivenessBinding::inflate, true
) {
    private val viewModel: LivenessViewModel by viewModel()

    private lateinit var cameraExecutor: ExecutorService
    private var countdownHandler: TextViewCountDownTimer? = null
    private var imageCapture: ImageCapture? = null

    private lateinit var detector: FaceDetector
    private var zipImages: MutableList<File> = mutableListOf()

    private var isImageSaved = true
    private var isZipSaved = false
    private val limitImages by lazy {
        when (VerificationPage.SharedInstance.verificationType) {
            VerificationType.KYC -> 15
            VerificationType.TWO_FA_REG -> 1
            VerificationType.TWO_FA_AUTH -> 15
            VerificationType.TWO_FA_UPDATE -> 1
            else -> 15
        }
    }
    private lateinit var imageDir: File
    private val verifType = VerificationPage.SharedInstance.verificationType
    private val mediaActionSound: MediaActionSound by lazy {
        MediaActionSound()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarTitle.text =
            when (verifType) {
                VerificationType.KYC -> getString(R.string.verifikasi_wajah)
                VerificationType.TWO_FA_REG -> getString(R.string.registrasi_biometrik)
                VerificationType.TWO_FA_AUTH -> getString(R.string.autentikasi_biometrik)
                VerificationType.TWO_FA_UPDATE -> getString(R.string.update_biometrik)
                else -> getString(R.string.verifikasi_wajah)
            }

        cameraExecutor = Executors.newSingleThreadExecutor()
        detector = FaceDetection.getClient(
            FaceDetectorOptions.Builder()
                .setContourMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                .build()
        )

        imageDir = File(requireActivity().cacheDir.absolutePath, "liveness")
        if (!imageDir.exists()) {
            imageDir.mkdirs()
        }

        if (PermissionHelper.locationPermissionsGranted(requireContext())) {
            if (PermissionHelper.cameraPermissionsGranted(requireContext())) {
                startCamera()
            } else {
                PermissionHelper.showBottomPermissionCamera(requireContext()) {
                    cameraPermissionLauncher.launch(PermissionHelper.CAMERA_PERMISSION)
                }
            }
        } else {
            PermissionHelper.showBottomPermissionLocation(requireContext()) {
                locationPermissionLauncher.launch(PermissionHelper.LOCATION_PERMISSIONS)
            }
        }
    }

    override fun onClickHandler() {
        with(binding) {
            btnBack.setOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        var isDetected = false

        with(binding) {
            countdownHandler = TextViewCountDownTimer(3000, 1000, txtCountdownTimer)
            cameraProviderFuture.addListener({
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

                // Preview
                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(cameraPreview.surfaceProvider)
                }

                var progressPercentage: Int

                imageCapture = ImageCapture.Builder()
                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                    .build()

                val imageAnalyzer = ImageAnalysis.Builder().build().also { imageAnalysis ->
                    imageAnalysis.setAnalyzer(cameraExecutor, PreviewAnalyzer { _ ->
                        lifecycleScope.launch {
                            if (isDetected) {
                                if (!countdownHandler!!.isTimerStarted) {
                                    toolbarCaption.text = getString(R.string.liveness_caption_start)
                                    countdownHandler!!.start()
                                }
                                if (!countdownHandler!!.isTimerFinished) return@launch
                                if (zipImages.size < limitImages) {
                                    progressPercentage =
                                        (((zipImages.size.toDouble() + 1.0) / limitImages.toDouble()) * 100).toInt()
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        circularProgressIndicator.setProgress(
                                            progressPercentage,
                                            true
                                        )
                                    } else {
                                        circularProgressIndicator.progress = progressPercentage
                                    }

                                    if (isImageSaved) {
                                        takePicture()
                                        isImageSaved = false
                                    }
                                } else {
                                    cameraProvider.unbindAll()
                                    if (!isZipSaved) {
                                        if (verifType == VerificationType.KYC || verifType == VerificationType.TWO_FA_AUTH) {
                                            saveToZip()
                                        } else {
                                            saveToArray()
                                        }
                                    }
                                }
                            } else {
                                if (cameraPreview.bitmap != null) {
                                    detector.process(cameraPreview.bitmap!!, 0)
                                        .addOnSuccessListener { faces ->
                                            if (faces.size > 0) {
                                                isDetected = true
                                            }
                                        }.addOnFailureListener {
                                            Log.d(TAG, "Detect Face error ${it.message}")
                                        }
                                }
                            }
                        }

                    })
                }
                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        this@LivenessFragment,
                        CameraSelector.DEFAULT_FRONT_CAMERA,
                        preview,
                        imageCapture,
                        imageAnalyzer
                    )

                } catch (exc: Exception) {
                    Log.e(TAG, "Use case binding failed", exc)
                }

            }, ContextCompat.getMainExecutor(requireContext()))
        }
    }

    private fun restartCamera() {
        restartFragment(R.id.livenessFragment)
    }

    private fun takePicture() {
        val imageCapture = imageCapture ?: return

        val file = File(imageDir, "images_${zipImages.size + 1}.jpeg")

        val outputFileOptions = ImageCapture.OutputFileOptions
            .Builder(
                File(imageDir, "images_${zipImages.size + 1}.jpeg")
            )
            .setMetadata(ImageCapture.Metadata().also {
                it.isReversedHorizontal = true
            }).build()

        imageCapture.takePicture(
            outputFileOptions,
            Executors.newSingleThreadExecutor(),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    lifecycleScope.launch(Dispatchers.Main) {
                        mediaActionSound.play(MediaActionSound.SHUTTER_CLICK)
                        zipImages.add(file)
                        isImageSaved = true
                        if (zipImages.size >= limitImages) {
                            binding.circularProgressIndicator.progress = 100
                            resultHandler.isLoading(true)
                        }
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.d(TAG, "error capture " + exception.message)
                }

            }
        )
    }

    private fun saveToZip() {
        lifecycleScope.launch(Dispatchers.Main) {
            zipImages.forEachIndexed { index, file ->
                val encoded = CamHelper.rotateImage(Uri.fromFile(file).path.toString())
                val zipFile = File(imageDir, "images_${index + 1}.zip")
                CommonUtils.base64ToZip(encoded, zipFile, "images_${index + 1}")
                kycOrAuth(zipFile, index + 1)
            }
            isZipSaved = true
        }
    }

    private fun kycOrAuth(zipFile: File, index: Int) {
        lifecycleScope.launch(Dispatchers.Main) {
            val listHandleError = listOf(
                ResponseCode.InvalidLandmark,
                ResponseCode.Spoofing,
                ResponseCode.FaceNotMatch,
                ResponseCode.MethodArgNotValid
            )
            viewModel.kycOrAuth(verifType!!, token.toString(), zipFile, index, index == limitImages)
                .observe(viewLifecycleOwner) { result ->
                    if (index == limitImages) {
                        resultHandler.handle(result, listHandleError, handleError = {
                            when (it?.code) {
                                ResponseCode.InvalidLandmark.code -> {
                                    VPUtils.generalBottomSheet(
                                        builder = bottomSheet,
                                        title = getString(R.string.face_not_detected),
                                        caption = getString(R.string.face_not_detected_caption),
                                        btnCancelTitle = getString(R.string.cancel),
                                        btnOkTitle = getString(R.string.verification),
                                        illustration = R.drawable.ill_peringatan,
                                        onCancelClick = {
                                            activity?.finish()
                                        }
                                    ) {
                                        restartCamera()
                                    }
                                }
                                ResponseCode.Spoofing.code, ResponseCode.FaceNotMatch.code -> {
                                    VPUtils.generalBottomSheet(
                                        builder = bottomSheet,
                                        title = getString(R.string.detected_spoofing),
                                        caption = getString(R.string.detected_spoofing_caption),
                                        btnCancelTitle = getString(R.string.cancel),
                                        btnOkTitle = getString(R.string.try_again),
                                        illustration = R.drawable.ill_peringatan,
                                        onCancelClick = {
                                            activity?.finish()
                                        }
                                    ) {
                                        restartCamera()
                                    }
                                }

                                ResponseCode.MethodArgNotValid.code -> {
                                    VPUtils.generalBottomSheet(
                                        bottomSheet,
                                        getString(com.dokuwallet.coresdk.R.string.general_alert_title),
                                        result.error?.message.toString(),
                                        btnOkTitle = getString(com.dokuwallet.coresdk.R.string.ok_title)
                                    )
                                }

                                else -> return@handle
                            }
                        }) {
                            if (VerificationPage.SharedInstance.verificationType == VerificationType.KYC) {
                                setStageAndFaceMatch()
                            } else {
                                resultHandler.isLoading(false)
                                VerificationPage.SharedInstance.VP_STATUS_VALUE = true
                                VPUtils.intentToEndView(requireActivity())
                            }
                        }
                    }
                }
        }
    }

    private fun setStageAndFaceMatch() {
        viewModel.setStage(token.toString(), 6).observe(viewLifecycleOwner) { result ->
            resultHandler.handle(result) {
                viewModel.faceMatch(token.toString()).observe(viewLifecycleOwner) { result ->
                    resultHandler.handle(result) {
                        resultHandler.isLoading(false)
                        VerificationPage.SharedInstance.VP_STATUS_VALUE = true
                        VPUtils.intentToEndView(requireActivity())
                    }
                }
            }
        }
    }

    private fun saveToArray() {
        val encoded = CamHelper.rotateImage(Uri.fromFile(zipImages[0]).path.toString())
        regOrUpdate(CaptureIdReq(arrayListOf(encoded)))
        isZipSaved = true
    }

    private fun regOrUpdate(images: CaptureIdReq) {
        val listHandleError = listOf(
            ResponseCode.InvalidLandmark,
            ResponseCode.Spoofing,
            ResponseCode.MethodArgNotValid
        )
        viewModel.regOrUpdate(verifType!!, token.toString(), images)
            .observe(viewLifecycleOwner) { result ->
                resultHandler.handle(result, listHandleError, handleError = {
                    when (it?.code) {
                        ResponseCode.InvalidLandmark.code -> {
                            VPUtils.generalBottomSheet(
                                builder = bottomSheet,
                                title = getString(R.string.face_not_detected),
                                caption = getString(R.string.face_not_detected_caption),
                                btnCancelTitle = getString(R.string.cancel),
                                btnOkTitle = getString(R.string.try_again),
                                illustration = R.drawable.ill_peringatan,
                                onCancelClick = {
                                    activity?.finish()
                                }
                            ) {
                                restartCamera()
                            }
                        }
                        ResponseCode.Spoofing.code -> {
                            VPUtils.generalBottomSheet(
                                builder = bottomSheet,
                                title = getString(R.string.face_not_detected),
                                caption = getString(R.string.detected_spoofing_caption),
                                btnCancelTitle = getString(R.string.cancel),
                                btnOkTitle = getString(R.string.try_again),
                                illustration = R.drawable.ill_peringatan,
                                onCancelClick = {
                                    activity?.finish()
                                }
                            ) {
                                restartCamera()
                            }
                        }

                        ResponseCode.MethodArgNotValid.code -> {
                            VPUtils.generalBottomSheet(
                                bottomSheet,
                                getString(com.dokuwallet.coresdk.R.string.general_alert_title),
                                result.error?.message.toString(),
                                btnOkTitle = getString(com.dokuwallet.coresdk.R.string.ok_title)
                            )
                        }

                        else -> return@handle
                    }
                }) {
                    resultHandler.isLoading(false)
                    if (verifType == VerificationType.TWO_FA_REG) {
                        findNavController().navigate(
                            LivenessFragmentDirections.actionLivenessFragmentToLivenessFinishFragment()
                                .setIsSuccess(it?.result == "SUCCESS")
                                .setErrorCode(it?.errorCode)
                        )
                    } else {
                        findNavController().navigate(
                            LivenessFragmentDirections.actionLivenessFragmentToLivenessFinishFragment()
                                .setIsUpdated(it?.result == "SUCCESS")
                                .setErrorCode(it?.errorCode)
                        )
                    }

                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (countdownHandler != null) {
            countdownHandler!!.cancel()
            cameraExecutor.shutdown()
        }
        mediaActionSound.release()
    }

    private val locationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            var granted = true
            permissions.entries.forEach {
                if (!it.value) {
                    granted = false
                }
            }
            if (granted) {
                if (PermissionHelper.cameraPermissionsGranted(requireContext())) {
                    startCamera()
                } else {
                    PermissionHelper.showBottomPermissionCamera(requireContext()) {
                        cameraPermissionLauncher.launch(PermissionHelper.CAMERA_PERMISSION)
                    }
                }
            } else {
                Toast.makeText(context, "Izin Lokasi Ditolak", Toast.LENGTH_SHORT).show()
            }
        }

    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            startCamera()
        } else {
            Toast.makeText(context, "Izin Camera Ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    private class PreviewAnalyzer(private val listener: (Double) -> Unit) : ImageAnalysis.Analyzer {

        private fun ByteBuffer.toByteArray(): ByteArray {
            rewind()
            val data = ByteArray(remaining())
            get(data)
            return data
        }

        override fun analyze(image: ImageProxy) {
            val buffer = image.planes[0].buffer
            val data = buffer.toByteArray()
            val pixels = data.map { it.toInt() and 0xFF }
            val avr = pixels.average()

            listener(avr)

            image.close()
        }
    }

    companion object {
        const val TAG = "livenessFragment"
    }
}