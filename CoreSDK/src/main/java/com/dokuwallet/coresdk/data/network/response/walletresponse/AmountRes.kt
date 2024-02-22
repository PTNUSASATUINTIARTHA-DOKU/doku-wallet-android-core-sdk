package com.dokuwallet.coresdk.data.network.response.walletresponse

import com.google.gson.annotations.SerializedName

data class AmountRes(
    @field:SerializedName("value")
    val value: String? = null,

    @field:SerializedName("currency")
    val currency: String? = null,
)