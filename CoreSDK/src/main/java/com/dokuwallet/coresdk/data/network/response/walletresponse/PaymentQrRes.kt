package com.dokuwallet.coresdk.data.network.response.walletresponse

import com.google.gson.annotations.SerializedName

data class PaymentQrRes(
    @field:SerializedName("responseCode")
    val responseCode: String? = null,

    @field:SerializedName("responseMessage")
    val responseMessage: String? = null,

    @field:SerializedName("referenceNo")
    val referenceNo: String? = null,

    @field:SerializedName("partnerReferenceNo")
    val partnerReferenceNo: String? = null,

    @field:SerializedName("transactionDate")
    val transactionDate: String? = null,

    @field:SerializedName("amount")
    val amount: AmountRes? = null,

    @field:SerializedName("feeAmount")
    val feeAmount: AmountRes? = null,

    @field:SerializedName("additionalInfo")
    val additionalInfo: AdditionalInfoRes? = null
)