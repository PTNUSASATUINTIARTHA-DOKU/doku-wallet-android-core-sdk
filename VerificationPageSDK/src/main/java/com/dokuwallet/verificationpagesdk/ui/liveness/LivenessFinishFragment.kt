package com.dokuwallet.verificationpagesdk.ui.liveness

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.dokuwallet.verificationpagesdk.R
import com.dokuwallet.verificationpagesdk.VpSDKBaseFragment
import com.dokuwallet.verificationpagesdk.databinding.VpSdkFragmentFinishBinding
import com.dokuwallet.verificationpagesdk.enums.ResponseCode
import com.dokuwallet.verificationpagesdk.utils.VPUtils
import com.dokuwallet.verificationpagesdk.utils.VerificationPage

internal class LivenessFinishFragment : VpSDKBaseFragment<VpSdkFragmentFinishBinding>(
    VpSdkFragmentFinishBinding::inflate, true
) {
    private val args: LivenessFinishFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()
    }

    override fun onClickHandler() {
        binding.btnClose.setOnClickListener {
            VPUtils.intentToEndView(requireActivity(), true)
        }
    }

    private fun setData() {
        with(binding) {
            when {
                args.isSuccess -> {
                    VerificationPage.SharedInstance.VP_STATUS_VALUE = args.isSuccess
                    Glide.with(requireContext())
                        .load(R.drawable.ill_success_registration)
                        .into(imgFinish)

                    txtTitle.text = getString(R.string.finish_success_title)
                    txtDesc.text = getString(R.string.finish_success_desc)
                }
                args.isUpdated -> {
                    VerificationPage.SharedInstance.VP_STATUS_VALUE = args.isUpdated
                    Glide.with(requireContext())
                        .load(R.drawable.ill_success_registration)
                        .into(imgFinish)

                    txtTitle.text = getString(R.string.finish_success_title)
                    txtDesc.text = getString(R.string.success_update_caption)
                }
                else -> {
                    VerificationPage.SharedInstance.VP_STATUS_VALUE = false
                    txtError.visibility = View.VISIBLE
                    btnPositive.visibility = View.VISIBLE
                    btnNegative.visibility = View.VISIBLE

                    when (args.errorCode) {
                        ResponseCode.UnexpectedException.code -> {
                            Glide.with(requireContext())
                                .load(R.drawable.ill_system_failure)
                                .into(imgFinish)

                            txtTitle.text = getString(R.string.finish_failed_title)
                            txtDesc.text = getString(R.string.finish_failed_desc)
                            txtError.text = getString(R.string.finish_failed_error, args.errorCode)

                            btnPositive.apply {
                                setBackgroundColor(Color.parseColor(VerificationPage.clientTheme.primaryButton.background))
                                setTextColor(Color.parseColor(VerificationPage.clientTheme.primaryButton.color))

                                setOnClickListener {
                                    activity?.finish()
                                    VerificationPage.SharedInstance.restartFlow()
                                }
                            }

                            btnNegative.apply {
                                setBackgroundColor(Color.parseColor(VerificationPage.clientTheme.secondaryButton.background))
                                setTextColor(Color.parseColor(VerificationPage.clientTheme.secondaryButton.color))
                                strokeColor =
                                    ColorStateList.valueOf(Color.parseColor(VerificationPage.clientTheme.secondaryButton.border))

                                setOnClickListener {
                                    val recipients = arrayOf("care@doku.com")
                                    val subject = "Keluhan SDK Verification Page"

                                    val intent = Intent(Intent.ACTION_SENDTO)
                                    intent.data = Uri.parse("mailto:")
                                    intent.putExtra(Intent.EXTRA_EMAIL, recipients)
                                    intent.putExtra(Intent.EXTRA_SUBJECT, subject)

                                    val packageManager = requireActivity().packageManager
                                    val emailApps = packageManager?.queryIntentActivities(intent, 0)

                                    if (emailApps?.isNotEmpty() == true) {
                                        startActivity(Intent.createChooser(intent, "Send Email"))
                                    } else {
                                        Toast.makeText(
                                            activity,
                                            "Email tidak ditemukan",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        }
                        else -> {
                            Glide.with(requireContext())
                                .load(R.drawable.ill_failed_registration)
                                .into(imgFinish)

                            txtTitle.text = getString(R.string.unexpected_exception_title)
                            txtDesc.text = getString(R.string.unexpected_exception_caption)
                            txtError.text = getString(R.string.finish_failed_error, args.errorCode)

                            btnPositive.apply {
                                setBackgroundColor(Color.parseColor(VerificationPage.clientTheme.primaryButton.background))
                                setTextColor(Color.parseColor(VerificationPage.clientTheme.primaryButton.color))

                                setOnClickListener {
                                    VerificationPage.SharedInstance.start2FaRegisterFlow(
                                        VerificationPage.SharedInstance.rootFragment!!,
                                        VerificationPage.SharedInstance.endViewFragment
                                    )
                                    activity?.finish()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}