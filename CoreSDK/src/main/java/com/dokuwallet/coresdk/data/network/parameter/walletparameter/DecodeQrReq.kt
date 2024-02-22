package com.dokuwallet.coresdk.data.network.parameter.walletparameter

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DecodeQrReq(
    @field:SerializedName("partnerReferenceNo")
    val originalPartnerReferenceNo: String,

    @field:SerializedName("qrContent")
    val qrContent: String,

    @field:SerializedName("scanTime")
    val scanTime: String,
)