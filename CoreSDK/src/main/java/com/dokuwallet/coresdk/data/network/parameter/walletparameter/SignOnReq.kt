package com.dokuwallet.coresdk.data.network.parameter.walletparameter

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SignOnReq(
    @field:SerializedName("clientId")
    val clientId: String,

    @field:SerializedName("clientSecret")
    val clientSecret: String,

    @field:SerializedName("systrace")
    val systrace: String,

    @field:SerializedName("words")
    val words: String,

    @field:SerializedName("version")
    val version: String,
)
