package com.dokuwallet.coresdk.data.network.response.walletresponse

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
internal data class CheckQrisRes(
    @field:SerializedName("dpMallId")
    val dpMallId: Int? = null,

    @field:SerializedName("transactionId")
    val transactionId: String? = null,

    @field:SerializedName("amount")
    val amount: Any? = null,

    @field:SerializedName("result")
    val result: String? = null,

    @field:SerializedName("transactionDateTime")
    val transactionDateTime: String? = null,

    @field:SerializedName("referenceId")
    val referenceId: String? = null,

    @field:SerializedName("clientId")
    val clientId: String? = null,

    @field:SerializedName("responseCode")
    val responseCode: String? = null,

    @field:SerializedName("responseMessage")
    val responseMessage: ResponseMessageRes? = null
)
