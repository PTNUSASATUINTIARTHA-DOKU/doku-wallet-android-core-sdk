package com.dokuwallet.coresdk.data.network.response.walletresponse

import com.google.gson.annotations.SerializedName

data class QueryQrRes(
    @field:SerializedName("responseCode")
    val responseCode: String? = null,

    @field:SerializedName("responseMessage")
    val responseMessage: String? = null,

    @field:SerializedName("originalReferenceNo")
    val originalReferenceNo: String? = null,

    @field:SerializedName("originalPartnerReferenceNo")
    val originalPartnerReferenceNo: String? = null,

    @field:SerializedName("serviceCode")
    val serviceCode: String? = null,

    @field:SerializedName("latestTransactionStatus")
    val latestTransactionStatus: String? = null,

    @field:SerializedName("transactionStatusDesc")
    val transactionStatusDesc: String? = null,

    @field:SerializedName("paidTime")
    val paidTime: String? = null,

    @field:SerializedName("amount")
    val amount: AmountRes? = null,

    @field:SerializedName("feeAmount")
    val feeAmount: AmountRes? = null,

    @field:SerializedName("additionalInfo")
    val additionalInfo: AdditionalInfoRes? = null,

    )
