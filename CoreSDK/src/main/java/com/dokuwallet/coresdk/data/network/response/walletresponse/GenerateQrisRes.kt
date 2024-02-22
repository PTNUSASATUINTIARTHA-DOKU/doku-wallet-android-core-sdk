package com.dokuwallet.coresdk.data.network.response.walletresponse

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
internal data class GenerateQrisRes(
    @field:SerializedName("qrCode")
    val qrCode: String? = null,

    @field:SerializedName("transactionId")
    val transactionId: String? = null,

    @field:SerializedName("responseCode")
    val responseCode: String? = null,

    @field:SerializedName("responseMessage")
    val responseMessage: ResponseMessageRes? = null
)