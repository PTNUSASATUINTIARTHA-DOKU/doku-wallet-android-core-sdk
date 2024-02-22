package com.dokuwallet.coresdk.data.network.response.walletresponse

import com.google.gson.annotations.SerializedName

data class GenerateQrRes(
    @field:SerializedName("responseCode")
    val responseCode: String? = null,

    @field:SerializedName("responseMessage")
    val responseMessage: String? = null,

    @field:SerializedName("referenceNo")
    val referenceNo: String? = null,

    @field:SerializedName("partnerReferenceNo")
    val partnerReferenceNo: String? = null,

    @field:SerializedName("qrContent")
    val qrContent: String? = null,

    @field:SerializedName("terminalId")
    val terminalId: String? = null
)