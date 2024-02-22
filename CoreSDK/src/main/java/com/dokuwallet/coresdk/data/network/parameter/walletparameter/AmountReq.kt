package com.dokuwallet.coresdk.data.network.parameter.walletparameter

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AmountReq(
    @field:SerializedName("value")
    val value: String? = null,

    @field:SerializedName("currency")
    val currency: String? = null,
)