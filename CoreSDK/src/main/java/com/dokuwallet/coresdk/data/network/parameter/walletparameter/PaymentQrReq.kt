package com.dokuwallet.coresdk.data.network.parameter.walletparameter

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PaymentQrReq(
    @field:SerializedName("partnerReferenceNo")
    val partnerReferenceNo: String,

    @field:SerializedName("amount")
    val amount: AmountReq,

    @field:SerializedName("feeAmount")
    val feeAmount: AmountReq,

    @field:SerializedName("additionalInfo")
    val additionalInfo: AdditionalInfoPaymentReq? = null,
)