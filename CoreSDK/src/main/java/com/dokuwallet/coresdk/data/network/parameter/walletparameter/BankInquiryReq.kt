package com.dokuwallet.coresdk.data.network.parameter.walletparameter

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class BankInquiryReq(
    @field:SerializedName("partnerReferenceNo")
    val partnerReferenceNo: String,

    @field:SerializedName("customerNumber")
    val customerNumber: String,

    @field:SerializedName("amount")
    val amount: AmountReq,

    @field:SerializedName("beneficiaryAccountNumber")
    val beneficiaryAccountNumber: String,

    @field:SerializedName("additionalInfo")
    val additionalInfo: AdditionalInfoReq,
)