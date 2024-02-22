package com.dokuwallet.coresdk.data.network.parameter.walletparameter

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AdditionalInfoReq(
    @field:SerializedName("postalCode")
    val postalCode: String? = null,

    @field:SerializedName("feeType")
    val feeType: String? = null,

    @field:SerializedName("deviceId")
    val deviceId: String? = null,

    @field:SerializedName("bankCode")
    val bankCode: String? = null,

    @field:SerializedName("referenceNo")
    val referenceNo: String? = null,

    @field:SerializedName("accountId")
    val accountId: String? = null,

    @field:SerializedName("qrContent")
    val qrContent: String? = null,
)