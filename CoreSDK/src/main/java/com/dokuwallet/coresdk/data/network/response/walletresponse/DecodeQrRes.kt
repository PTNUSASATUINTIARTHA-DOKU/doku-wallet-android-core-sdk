package com.dokuwallet.coresdk.data.network.response.walletresponse

import com.google.gson.annotations.SerializedName

data class DecodeQrRes(
    @field:SerializedName("responseCode")
    val responseCode: String? = null,

    @field:SerializedName("responseMessage")
    val responseMessage: String? = null,

    @field:SerializedName("referenceNo")
    val referenceNo: String? = null,

    @field:SerializedName("partnerReferenceNo")
    val partnerReferenceNo: String? = null,

    @field:SerializedName("merchantName")
    val merchantName: String? = null,

    @field:SerializedName("merchantCategory")
    val merchantCategory: String? = null,

    @field:SerializedName("merchantLocation")
    val merchantLocation: String? = null,

    @field:SerializedName("transactionAmount")
    val transactionAmount: AmountRes? = null,

    @field:SerializedName("feeAmount")
    val feeAmount: AmountRes? = null,

    @field:SerializedName("additionalInfo")
    val additionalInfo: AdditionalInfoRes? = null
)