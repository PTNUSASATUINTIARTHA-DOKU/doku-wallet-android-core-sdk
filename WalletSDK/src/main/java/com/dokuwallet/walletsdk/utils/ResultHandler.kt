package com.dokuwallet.walletsdk.utils

import android.app.Dialog
import android.content.Context
import android.util.Log
import com.dokuwallet.coresdk.utils.CommonUtils
import com.dokuwallet.coresdk.vo.ErrorData
import com.dokuwallet.coresdk.vo.Resource
import com.google.android.material.bottomsheet.BottomSheetDialog

internal class ResultHandler(
    private val context: Context
) {

    fun <T> handle(
        result: Resource<T>,
        handleError: (ErrorData?) -> Unit = {},
        isSuccess: (T) -> Unit
    ) {
        when (result) {
            is Resource.Error -> {
//                isLoading(false)
//                if (errorCodes.any { it == result.error?.code }) {
                    handleError(result.error)
//                }
            }

            is Resource.Loading -> {
                Log.d("TAG", "Loading..")
            }

            is Resource.Success -> {
                Log.d("TAG", result.data.toString())
                if (result.data == null) return
                isSuccess(result.data!!)
            }
        }
    }

//    fun isLoading(state: Boolean) {
//        if (state) {
//            showActivityIndicator()
//        } else {
//            hideActivityIndicator()
//        }
//    }
//
//    private fun showActivityIndicator() {
//        hideActivityIndicator()
//        indicatorView = CommonUtils.showLoadingDialog(context)
//    }
//
//    private fun hideActivityIndicator() {
//        if (indicatorView != null && indicatorView?.isShowing == true) {
//            indicatorView!!.cancel()
//        }
//    }
}