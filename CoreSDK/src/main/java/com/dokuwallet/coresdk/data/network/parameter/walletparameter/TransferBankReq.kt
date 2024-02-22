package com.dokuwallet.coresdk.data.network.parameter.walletparameter

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TransferBankReq(
    @field:SerializedName("accessToken")
    val accessToken: String,

    @field:SerializedName("clientId")
    val clientId: String,

    @field:SerializedName("accountId")
    val accountId: String,

    @field:SerializedName("urlIntent")
    val urlIntent: String,

    @field:SerializedName("transactionId")
    val transactionId: String,

    @field:SerializedName("systrace")
    val systrace: String,

    @field:SerializedName("words")
    val words: String,

    @field:SerializedName("version")
    val version: String,
)
