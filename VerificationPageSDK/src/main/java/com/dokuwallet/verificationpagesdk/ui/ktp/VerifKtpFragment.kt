package com.dokuwallet.verificationpagesdk.ui.ktp

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Base64
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dokuwallet.coresdk.data.network.parameter.vpparameter.CaptureIdReq
import com.dokuwallet.verificationpagesdk.VpSDKBaseFragment
import com.dokuwallet.verificationpagesdk.databinding.VpSdkFragmentVerificationBinding
import com.dokuwallet.verificationpagesdk.utils.CamHelper
import com.dokuwallet.verificationpagesdk.utils.VerificationPage
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream


internal class VerifKtpFragment : VpSDKBaseFragment<VpSdkFragmentVerificationBinding>(
    VpSdkFragmentVerificationBinding::inflate
) {

    private val args: VerifKtpFragmentArgs by navArgs()

    private val viewModel: KtpViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClientTheme()
        binding.imgVerif.setImageURI(CamHelper.getUriFromFile(baseCachePath, true))
    }

    private fun setClientTheme() {
        with(binding) {
            btnSave.setTextColor(Color.parseColor(VerificationPage.clientTheme.primaryButton.color))
            btnSave.setBackgroundColor(Color.parseColor(VerificationPage.clientTheme.primaryButton.background))
            btnRetake.setTextColor(Color.parseColor(VerificationPage.clientTheme.secondaryButton.color))
            btnRetake.setBackgroundColor(Color.parseColor(VerificationPage.clientTheme.secondaryButton.background))
            btnRetake.strokeColor =
                ColorStateList.valueOf(Color.parseColor(VerificationPage.clientTheme.secondaryButton.border))
            setupKycFooter()
        }
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
                val bitmapKtp = (binding.imgVerif.drawable as BitmapDrawable).bitmap
                val outStream = ByteArrayOutputStream()
                bitmapKtp.compress(Bitmap.CompressFormat.JPEG, 50, outStream)
                val byteKtp = outStream.toByteArray()
                captureId(
                    images = CaptureIdReq(
                        arrayListOf(
                            Base64.encodeToString(
                                byteKtp,
                                Base64.DEFAULT
                            ).replace("\n", "")
                        )
                    )
                )
            }
        }
    }

    private fun captureId(images: CaptureIdReq) {
        if (args.isRetake || isBackPressed || (VerificationPage.stage!! >= 2)) {
            viewModel.captureId(token.toString(), images)
                .observe(viewLifecycleOwner) { result ->
                    resultHandler.handle(result) {
                        requestOcr()
                    }
                }
        } else {
            viewModel.setStage(token.toString(), 2).observe(viewLifecycleOwner) { result ->
                resultHandler.handle(result) {
                    viewModel.captureId(token.toString(), images)
                        .observe(viewLifecycleOwner) { result ->
                            resultHandler.handle(result) {
                                requestOcr()
                            }
                        }
                }
            }
        }
    }

    private fun requestOcr() {
        viewModel.requestOcr(token.toString()).observe(viewLifecycleOwner) { result ->
            resultHandler.handle(result) {
                if (args.isRetake) {
                    navigateToIdentity()
                } else setStage()
            }
        }
    }

    private fun setStage() {
        if (isBackPressed) {
            navigateToSelfie()
        } else {
            viewModel.setStage(token.toString(), 3).observe(viewLifecycleOwner) { result ->
                resultHandler.handle(result) {
                    navigateToSelfie()
                }
            }
        }
    }

    private fun navigateToSelfie() {
        resultHandler.isLoading(false)
        findNavController().navigate(
            VerifKtpFragmentDirections.actionVerifKtpFragmentToSelfieFragment()
        )
    }

    private fun navigateToIdentity() {
        resultHandler.isLoading(false)
        findNavController().navigate(
            VerifKtpFragmentDirections.actionVerifKtpFragmentToIdentityFragment()
        )
    }
}
