package com.dokuwallet.coresdk.data.network.parameter.walletparameter

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class BankTransferReq(
    @field:SerializedName("partnerReferenceNo")
    val partnerReferenceNo: String,

    @field:SerializedName("customerNumber")
    val customerNumber: String,

    @field:SerializedName("accountType")
    val accountType: String,

    @field:SerializedName("beneficiaryAccountNumber")
    val beneficiaryAccountNumber: String,

    @field:SerializedName("beneficiaryBankCode")
    val beneficiaryBankCode: String,

    @field:SerializedName("amount")
    val amount: AmountReq,

    @field:SerializedName("additionalInfo")
    val additionalInfo: AdditionalInfoReq,
)