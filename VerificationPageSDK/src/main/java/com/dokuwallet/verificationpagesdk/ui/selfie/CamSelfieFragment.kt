package com.dokuwallet.verificationpagesdk.ui.selfie

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
import com.dokuwallet.verificationpagesdk.VpSDKBaseFragment
import com.dokuwallet.verificationpagesdk.databinding.VpSdkFragmentCamSelfieBinding
import com.dokuwallet.verificationpagesdk.utils.CamHelper
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

internal class CamSelfieFragment : VpSDKBaseFragment<VpSdkFragmentCamSelfieBinding>(
    VpSdkFragmentCamSelfieBinding::inflate,
    true
) {
    private val args: CamSelfieFragmentArgs by navArgs()

    private lateinit var cameraExecutor: ExecutorService
    private var imageCapture: ImageCapture? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraExecutor = Executors.newSingleThreadExecutor()

        startCamera()
    }

    override fun onClickHandler() {
        with(binding) {
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
                File("${requireActivity().cacheDir.absolutePath}/${CamHelper.selfieTempFile}")
            )
            .setMetadata(ImageCapture.Metadata().also {
                it.isReversedHorizontal = true
            }).build()

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
                            navigateToVerifSelfie()
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
                    CameraSelector.DEFAULT_FRONT_CAMERA,
                    preview,
                    imageCapture
                )

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun navigateToVerifSelfie() {
        findNavController().navigate(
            CamSelfieFragmentDirections.actionCamSelfieFragmentToVerifSelfieFragment()
        )
    }

    private fun navigateToIdentity() {
        findNavController().navigate(
            CamSelfieFragmentDirections.actionCamSelfieFragmentToVerifSelfieFragment()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cameraExecutor.shutdown()
    }

    companion object {
        private const val TAG = "CamSelfieFragment"
    }
}