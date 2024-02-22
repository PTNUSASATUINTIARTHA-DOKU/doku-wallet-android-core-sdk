package com.dokuwallet.coresdk.data.network.response.walletresponse

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
internal data class BankInquiryRes(
    @field:SerializedName("responseCode")
    val responseCode: String? = null,

    @field:SerializedName("responseMessage")
    val responseMessage: String? = null,

    @field:SerializedName("referenceNo")
    val referenceNo: String? = null,

    @field:SerializedName("partnerReferenceNo")
    val partnerReferenceNo: String? = null,

    @field:SerializedName("beneficiaryAccountNumber")
    val beneficiaryAccountNumber: String? = null,

    @field:SerializedName("beneficiaryAccountName")
    val beneficiaryAccountName: String? = null,

    @field:SerializedName("beneficiaryBankCode")
    val beneficiaryBankCode: String? = null,

    @field:SerializedName("amount")
    val amount: AmountRes? = null,
)