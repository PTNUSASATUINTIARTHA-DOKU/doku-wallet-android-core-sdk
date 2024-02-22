package com.dokuwallet.verificationpagesdk.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.dokuwallet.coresdk.databinding.DokuSdkBottomSheetAlertBinding
import com.dokuwallet.verificationpagesdk.R
import com.dokuwallet.verificationpagesdk.databinding.VpSdkBottomSheetKeyBinding
import com.google.android.material.bottomsheet.BottomSheetDialog


internal object VPUtils {

    fun generalBottomSheet(
        builder: BottomSheetDialog,
        title: String,
        caption: String,
        btnCancelTitle: String? = null,
        btnOkTitle: String? = null,
        illustration: Int = R.drawable.confirmation_alert,
        onCancelClick: () -> Unit = {},
        onActionClick: () -> Unit = {}
    ) {
        val context = builder.context

        val sheetBinding = DokuSdkBottomSheetAlertBinding.inflate(LayoutInflater.from(context))

        sheetBinding.apply {
            txtTitle.text = title
            txtCaption.text = caption

            imgAlert.setImageDrawable(ContextCompat.getDrawable(context, illustration))

            btnCancel.apply {
                setBackgroundColor(Color.parseColor(VerificationPage.clientTheme.secondaryButton.background))
                setTextColor(Color.parseColor(VerificationPage.clientTheme.secondaryButton.color))
                strokeColor =
                    ColorStateList.valueOf(Color.parseColor(VerificationPage.clientTheme.secondaryButton.border))

                visibility = if (btnCancelTitle?.isNotEmpty() == true) View.VISIBLE else View.GONE
                text = btnCancelTitle

                setOnClickListener {
                    onCancelClick()
                    builder.dismiss()
                }
            }

            btnOk.apply {
                setBackgroundColor(Color.parseColor(VerificationPage.clientTheme.primaryButton.background))
                setTextColor(Color.parseColor(VerificationPage.clientTheme.primaryButton.color))

                visibility = if (btnOkTitle?.isNotEmpty() == true) View.VISIBLE else View.GONE
                text = btnOkTitle

                setOnClickListener {
                    onActionClick()
                    builder.dismiss()
                }
            }
        }

        builder.apply {
            setCancelable(true)
            setContentView(sheetBinding.root)
            show()
        }
    }

    fun showBottomSheetKey(
        context: Context,
        title: String,
        caption: String,
        btnYesTitle: String? = null,
        btnNoTitle: String? = null,
        onAllowClick: () -> Unit = {},
        onCancelClick: () -> Unit = {}
    ) {
        val sheetBinding = VpSdkBottomSheetKeyBinding.inflate(LayoutInflater.from(context))
        val builder = BottomSheetDialog(context)

        sheetBinding.apply {
            txtTitle.text = title
            txtCaption.text = caption

            btnYes.apply {
                text = btnYesTitle ?: context.getString(R.string.izinkan)
                setBackgroundColor(Color.parseColor(VerificationPage.clientTheme.primaryButton.background))
                setTextColor(Color.parseColor(VerificationPage.clientTheme.primaryButton.color))

                setOnClickListener {
                    onAllowClick()
                    builder.dismiss()
                }
            }

            btnNo.apply {
                text = btnNoTitle ?: context.getString(com.dokuwallet.coresdk.R.string.nanti_saja)
                setBackgroundColor(Color.parseColor(VerificationPage.clientTheme.secondaryButton.background))
                setTextColor(Color.parseColor(VerificationPage.clientTheme.secondaryButton.color))
                strokeColor =
                    ColorStateList.valueOf(Color.parseColor(VerificationPage.clientTheme.secondaryButton.border))

                setOnClickListener {
                    onCancelClick()
                    builder.dismiss()
                }
            }
        }

        builder.apply {
            setCancelable(true)
            setContentView(sheetBinding.root)
            show()
        }
    }

    fun Button.updateColor() {
        backgroundTintList = if (isEnabled) {
            setTextColor(Color.parseColor(VerificationPage.clientTheme.primaryButton.color))
            ColorStateList.valueOf(Color.parseColor(VerificationPage.clientTheme.primaryButton.background))
        } else {
            setTextColor(context.getColor(com.dokuwallet.coresdk.R.color.gray_neutral_80))
            ColorStateList.valueOf(context.getColor(com.dokuwallet.coresdk.R.color.gray_neutral_20))
        }
    }

    fun EditText.stringTrim(): String = text.toString().trim()

    fun intentToEndView(activity: Activity, isStopProcess: Boolean = false) {
        if (VerificationPage.SharedInstance.endViewFragment != null) {
            Intent(
                activity,
                VerificationPage.SharedInstance.endViewFragment!!::class.java
            ).also { intent ->
                activity.finish()
                activity.startActivity(intent)
            }
        } else {
            if (isStopProcess) activity.finish()
        }
    }

    fun String.onlyCapsFirst(): String {
        val words = this.split(" ")
        val formattedWords = words.mapIndexed { index, word ->
            if (index == 0) {
                word.capitalize()
            } else {
                word.toLowerCase()
            }
        }
        return formattedWords.joinToString(" ")
    }
}