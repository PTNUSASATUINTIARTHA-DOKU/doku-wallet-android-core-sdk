package com.dokuwallet.verificationpagesdk.ui.ktp

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.dokuwallet.verificationpagesdk.VpSDKBaseFragment
import com.dokuwallet.verificationpagesdk.databinding.VpSdkFragmentKtpBinding
import com.dokuwallet.verificationpagesdk.utils.PermissionHelper
import com.dokuwallet.verificationpagesdk.utils.PermissionHelper.CAMERA_PERMISSION
import com.dokuwallet.verificationpagesdk.utils.PermissionHelper.LOCATION_PERMISSIONS
import com.dokuwallet.verificationpagesdk.utils.VerificationPage

internal class KtpFragment : VpSDKBaseFragment<VpSdkFragmentKtpBinding>(
    VpSdkFragmentKtpBinding::inflate
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
        binding.btnTakePhoto.setOnClickListener {
            if (PermissionHelper.locationPermissionsGranted(requireContext())) {
                if (PermissionHelper.cameraPermissionsGranted(requireContext())) {
                    navigateToCamKtp()
                } else {
                    PermissionHelper.showBottomPermissionCamera(requireContext()) {
                        cameraPermissionLauncher.launch(CAMERA_PERMISSION)
                    }
                }
            } else {
                PermissionHelper.showBottomPermissionLocation(requireContext()) {
                    locationPermissionLauncher.launch(LOCATION_PERMISSIONS)
                }
            }
        }
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
                PermissionHelper.showBottomPermissionCamera(requireContext()) {
                    cameraPermissionLauncher.launch(CAMERA_PERMISSION)
                }
            } else {
                Toast.makeText(context, "Izin Lokasi Ditolak", Toast.LENGTH_SHORT).show()
            }
        }

    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            navigateToCamKtp()
        } else {
            Toast.makeText(context, "Izin Camera Ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToCamKtp() {
        findNavController().navigate(KtpFragmentDirections.actionKtpFragmentToCamKtpFragment())
    }
}