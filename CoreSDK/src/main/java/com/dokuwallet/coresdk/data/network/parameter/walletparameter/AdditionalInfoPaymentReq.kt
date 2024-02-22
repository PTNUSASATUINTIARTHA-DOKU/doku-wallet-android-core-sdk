package com.dokuwallet.coresdk.data.network.parameter.walletparameter

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AdditionalInfoPaymentReq(
    @field:SerializedName("qrContent")
    val qrContent: String? = null,
)