package com.dokuwallet.coresdk.data.network.parameter.walletparameter

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AccountBindingReq(
    @field:SerializedName("partnerReferenceNo")
    val partnerReferenceNo: String,

    @field:SerializedName("redirectUrl")
    val redirectUrl: String,

    @field:SerializedName("successParams")
    val successParams: SuccessParamsReq,
)