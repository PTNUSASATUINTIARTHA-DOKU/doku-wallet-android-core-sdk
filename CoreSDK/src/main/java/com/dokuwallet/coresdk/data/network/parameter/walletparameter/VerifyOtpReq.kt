package com.dokuwallet.coresdk.data.network.parameter.walletparameter

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class VerifyOtpReq(
    @field:SerializedName("originalPartnerReferenceNo")
    val originalPartnerReferenceNo: String,

    @field:SerializedName("originalReferenceNo")
    val originalReferenceNo: String,

    @field:SerializedName("otp")
    val otp: String,

    @field:SerializedName("additionalInfo")
    val additionalInfo: AdditionalInfoReq? = null,
)