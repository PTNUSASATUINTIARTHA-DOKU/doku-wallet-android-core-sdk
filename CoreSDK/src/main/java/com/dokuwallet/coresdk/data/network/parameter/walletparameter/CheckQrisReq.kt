package com.dokuwallet.coresdk.data.network.parameter.walletparameter

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CheckQrisReq(
    @field:SerializedName("accessToken")
    val accessToken: String,

    @field:SerializedName("clientId")
    val clientId: String,

    @field:SerializedName("transactionId")
    val transactionId: String,

    @field:SerializedName("dpMallId")
    val dpMallId: String,

    @field:SerializedName("words")
    val words: String,

    @field:SerializedName("version")
    val version: String,
)
