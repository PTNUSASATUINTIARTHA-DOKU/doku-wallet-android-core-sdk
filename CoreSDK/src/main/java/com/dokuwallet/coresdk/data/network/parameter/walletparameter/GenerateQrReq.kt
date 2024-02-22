package com.dokuwallet.coresdk.data.network.parameter.walletparameter

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GenerateQrReq(
    @field:SerializedName("partnerReferenceNo")
    val partnerReferenceNo: String,

    @field:SerializedName("amount")
    val amount: AmountReq? = null,

    @field:SerializedName("feeAmount")
    val feeAmount: AmountReq? = null,

    @field:SerializedName("merchantId")
    val merchantId: String,

    @field:SerializedName("terminalId")
    val terminalId: String,

    @field:SerializedName("additionalInfo")
    val additionalInfo: AdditionalInfoReq
)

