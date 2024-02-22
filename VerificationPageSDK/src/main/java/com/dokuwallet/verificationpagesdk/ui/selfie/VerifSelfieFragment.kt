package com.dokuwallet.verificationpagesdk.ui.selfie

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.dokuwallet.coresdk.data.network.parameter.vpparameter.CaptureIdReq
import com.dokuwallet.verificationpagesdk.VpSDKBaseFragment
import com.dokuwallet.verificationpagesdk.databinding.VpSdkFragmentVerificationBinding
import com.dokuwallet.verificationpagesdk.utils.CamHelper
import com.dokuwallet.verificationpagesdk.utils.VerificationPage
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream

internal class VerifSelfieFragment : VpSDKBaseFragment<VpSdkFragmentVerificationBinding>(
    VpSdkFragmentVerificationBinding::inflate
) {

    private val viewModel: SelfieViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClientTheme()
        binding.imgVerif.setImageURI(CamHelper.getUriFromFile(baseCachePath, false))
    }

    private fun setClientTheme() {
        with(binding) {
            btnSave.setTextColor(Color.parseColor(VerificationPage.clientTheme.primaryButton.color))
            btnSave.setBackgroundColor(Color.parseColor(VerificationPage.clientTheme.primaryButton.background))
            btnRetake.setTextColor(Color.parseColor(VerificationPage.clientTheme.secondaryButton.color))
            btnRetake.setBackgroundColor(Color.parseColor(VerificationPage.clientTheme.secondaryButton.background))
            btnRetake.strokeColor =
                ColorStateList.valueOf(Color.parseColor(VerificationPage.clientTheme.secondaryButton.border))
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
            btnRetake.setOnClickListener {
                activity?.onBackPressed()
            }
            btnSave.setOnClickListener {
                resultHandler.isLoading(true)
                val bitmapSelfie = (binding.imgVerif.drawable as BitmapDrawable).bitmap
                val outStream = ByteArrayOutputStream()
                bitmapSelfie.compress(Bitmap.CompressFormat.JPEG, 10, outStream)
                val byteSelfie = outStream.toByteArray()
                val images = mutableListOf<String>()
                for (i in 1..15) {
                    images.add(Base64.encodeToString(byteSelfie, Base64.DEFAULT).replace("\n", ""))
                }
                Log.d("TAG", "length " + images.size.toString())
                captureSelfie(images = CaptureIdReq(images = images as ArrayList<String>))
            }
        }
    }

    private fun navigateToIdentity() {
        resultHandler.isLoading(false)
        findNavController().navigate(
            VerifSelfieFragmentDirections.actionVerifSelfieFragmentToIdentityFragment()
        )
    }

    private fun captureSelfie(images: CaptureIdReq) {
        viewModel.captureSelfie(token.toString(), images).observe(viewLifecycleOwner) { result ->
            resultHandler.handle(result) {
                navigateToIdentity()
            }
        }
    }
}