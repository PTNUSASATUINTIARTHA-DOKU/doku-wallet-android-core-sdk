package com.dokuwallet.verificationpagesdk.ui.selfie

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.dokuwallet.verificationpagesdk.VpSDKBaseFragment
import com.dokuwallet.verificationpagesdk.databinding.VpSdkFragmentSelfieBinding
import com.dokuwallet.verificationpagesdk.utils.PermissionHelper
import com.dokuwallet.verificationpagesdk.utils.VerificationPage

internal class SelfieFragment : VpSDKBaseFragment<VpSdkFragmentSelfieBinding>(
    VpSdkFragmentSelfieBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClientTheme()
    }

    private fun setClientTheme() {
        with(binding) {
            txtTitle.setTextColor(Color.parseColor(VerificationPage.clientTheme.primaryButton.background))
            viewProgress1.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(VerificationPage.clientTheme.primaryButton.background))
            viewProgress2.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(VerificationPage.clientTheme.primaryButton.background))
            btnTakePhoto.setTextColor(Color.parseColor(VerificationPage.clientTheme.primaryButton.color))
            btnTakePhoto.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(VerificationPage.clientTheme.primaryButton.background))
        }
        setupKycFooter()
    }

    private fun setupKycFooter() {
        if (VerificationPage.verificationPageConfig.hideKycFooter) {
            binding.footerInformation.root.visibility = View.GONE
        } else {
            binding.footerInformation.root.visibility = View.VISIBLE
        }
    }

    override fun onClickHandler() {
        with(binding) {
            btnTakePhoto.setOnClickListener {
                if (PermissionHelper.cameraPermissionsGranted(requireContext())) {
                    navigateToCamSelfie()
                } else {
                    PermissionHelper.showBottomPermissionCamera(requireContext()) {
                        cameraPermissionLauncher.launch(PermissionHelper.CAMERA_PERMISSION)
                    }
                }
            }
        }
    }

    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            navigateToCamSelfie()
        } else {
            Toast.makeText(context, "Izin Camera Ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToCamSelfie() {
        findNavController().navigate(
            SelfieFragmentDirections.actionSelfieFragmentToCamSelfieFragment()
        )
    }
}