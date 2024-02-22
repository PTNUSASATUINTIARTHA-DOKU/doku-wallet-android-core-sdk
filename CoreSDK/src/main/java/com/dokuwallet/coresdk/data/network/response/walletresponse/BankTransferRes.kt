package com.dokuwallet.coresdk.data.network.response.walletresponse

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
internal data class BankTransferRes(
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

    @field:SerializedName("referenceNumber")
    val referenceNumber: String? = null,
)