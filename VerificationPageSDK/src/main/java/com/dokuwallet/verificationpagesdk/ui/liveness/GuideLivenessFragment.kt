package com.dokuwallet.verificationpagesdk.ui.liveness

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.dokuwallet.verificationpagesdk.VpSDKBaseFragment
import com.dokuwallet.verificationpagesdk.databinding.VpSdkFragmentGuideLivenessBinding
import com.dokuwallet.verificationpagesdk.utils.PermissionHelper
import com.dokuwallet.verificationpagesdk.utils.VerificationPage

internal class GuideLivenessFragment : VpSDKBaseFragment<VpSdkFragmentGuideLivenessBinding>(
    VpSdkFragmentGuideLivenessBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClientTheme()
    }

    override fun onClickHandler() {
        with(binding) {
            btnStart.setOnClickListener {
                if (PermissionHelper.cameraPermissionsGranted(requireContext())) {
                    navigateToLiveness()
                } else {
                    PermissionHelper.showBottomPermissionCamera(requireContext()) {
                        cameraPermissionLauncher.launch(PermissionHelper.CAMERA_PERMISSION)
                    }
                }
            }
        }
    }

    private fun setClientTheme() {
        binding.materialCardView3.setBackgroundColor(Color.parseColor(VerificationPage.clientTheme.backgroundColor))
        binding.btnStart.setTextColor(Color.parseColor(VerificationPage.clientTheme.primaryButton.color))
        binding.btnStart.setBackgroundColor(Color.parseColor(VerificationPage.clientTheme.primaryButton.background))
    }

    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            navigateToLiveness()
        } else {
            Toast.makeText(context, "Izin Camera Ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToLiveness() {
        findNavController().navigate(
            GuideLivenessFragmentDirections.actionGuideLivenessFragmentToLivenessFragment()
        )
    }
}