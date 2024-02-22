package com.dokuwallet.verificationpagesdk.ui.ktp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dokuwallet.verificationpagesdk.R
import com.dokuwallet.verificationpagesdk.VpSDKBaseFragment
import com.dokuwallet.verificationpagesdk.databinding.VpSdkFragmentCamKtpBinding
import com.dokuwallet.verificationpagesdk.utils.CamHelper
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


internal class CamKtpFragment : VpSDKBaseFragment<VpSdkFragmentCamKtpBinding>(
    VpSdkFragmentCamKtpBinding::inflate, true
) {
    private val args: CamKtpFragmentArgs by navArgs()

    private lateinit var cameraExecutor: ExecutorService
    private var imageCapture: ImageCapture? = null

    private var isFlashOn = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraExecutor = Executors.newSingleThreadExecutor()

        startCamera()
    }

    override fun onClickHandler() {
        with(binding) {
            btnFlash.apply {
                setOnClickListener {
                    startCamera()
                    isFlashOn = !isFlashOn

                    setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            if (isFlashOn) R.drawable.ic_baseline_flash_on_24 else R.drawable.ic_baseline_flash_off_24
                        )
                    )
                }
            }
            btnCapture.setOnClickListener {
                takePicture()
            }
            btnBack.setOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    private fun takePicture() {
        val imageCapture = imageCapture ?: return

        val outputFileOptions = ImageCapture.OutputFileOptions
            .Builder(
                File("${requireActivity().cacheDir.absolutePath}/${CamHelper.ktpTempFile}")
            ).build()

        imageCapture.takePicture(
            outputFileOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    Log.d(TAG, outputFileResults.savedUri.toString())
                    outputFileResults.savedUri?.let {
                        if (args.isRetake) {
                            navigateToIdentity()
                        } else {
                            navigateToVerifKtp()
                        }
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e(TAG, "onError: ", exception)
                }

            }
        )
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding.camKtp.surfaceProvider)
            }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    imageCapture
                ).cameraControl.enableTorch(isFlashOn)

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun navigateToVerifKtp() {
        findNavController().navigate(
            CamKtpFragmentDirections.actionCamKtpFragmentToVerifKtpFragment()
        )
    }

    private fun navigateToIdentity() {
        findNavController().navigate(
            CamKtpFragmentDirections.actionCamKtpFragmentToVerifKtpFragment().setIsRetake(true)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cameraExecutor.shutdown()
    }

    companion object {
        private const val TAG = "CamKtpFragment"
    }
}