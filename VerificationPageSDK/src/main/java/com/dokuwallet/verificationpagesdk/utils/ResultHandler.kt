package com.dokuwallet.verificationpagesdk.utils

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.util.Log
import com.dokuwallet.coresdk.utils.CommonUtils
import com.dokuwallet.coresdk.vo.ErrorData
import com.dokuwallet.coresdk.vo.Resource
import com.dokuwallet.verificationpagesdk.enums.ResponseCode
import com.dokuwallet.verificationpagesdk.ui.VpSDKHomeActivity
import com.google.android.material.bottomsheet.BottomSheetDialog

internal class ResultHandler(
    private val context: Context,
    private var indicatorView: Dialog?,
    private val bottomSheet: BottomSheetDialog
) {

    fun <T> handle(
        result: Resource<T>,
        errorCodes: List<ResponseCode> = listOf(),
        handleError: (ErrorData?) -> Unit = {},
        isSuccess: (T?) -> Unit
    ) {
        when (result) {
            is Resource.Error -> {
                Log.d("TAG", "Error Code (${result.error?.code}): ${result.error?.message}")
                isLoading(false)

                if (errorCodes.any { it.code == result.error?.code }) {
                    handleError(result.error)
                } else {
                    if (result.error?.code == ResponseCode.UnexpectedException.code) {
                        Intent(context, VpSDKHomeActivity::class.java).also {
                            it.putExtra(VpSDKHomeActivity.ERROR_CODE_KEY, result.error?.code)
                            context.startActivity(it)
                        }
                    } else {
                        VPUtils.generalBottomSheet(
                            bottomSheet,
                            context.getString(com.dokuwallet.coresdk.R.string.general_alert_title),
                            context.getString(com.dokuwallet.coresdk.R.string.general_alert_caption),
                            btnOkTitle = context.getString(com.dokuwallet.coresdk.R.string.ok_title)
                        )
                    }
                }
            }
            is Resource.Loading -> {
                Log.d("TAG", "Loading..")
            }
            is Resource.Success -> {
                Log.d("TAG", result.data.toString())
                isSuccess(result.data)
            }
        }
    }

    fun isLoading(state: Boolean) {
        if (state) {
            showActivityIndicator()
        } else {
            hideActivityIndicator()
        }
    }

    private fun showActivityIndicator() {
        hideActivityIndicator()
        indicatorView = CommonUtils.showLoadingDialog(context)
    }

    private fun hideActivityIndicator() {
        if (indicatorView != null && indicatorView?.isShowing == true) {
            indicatorView!!.cancel()
        }
    }
}