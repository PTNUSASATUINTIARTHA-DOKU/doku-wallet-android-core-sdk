package com.dokuwallet.coresdk.data.network.parameter.walletparameter

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GenerateQrisReq(
    @field:SerializedName("accessToken")
    val accessToken: String,

    @field:SerializedName("clientId")
    val clientId: String,

    @field:SerializedName("dpMallId")
    val dpMallId: String,

    @field:SerializedName("terminalId")
    val terminalId: String,

    @field:SerializedName("postalCode")
    val postalCode: String,

    @field:SerializedName("amount")
    val amount: String? = null,

    @field:SerializedName("feeType")
    val feeType: String? = null,

    @field:SerializedName("conveniencesFee")
    val conveniencesFee: String? = null,

    @field:SerializedName("uuid")
    val uuid: String? = null,

    @field:SerializedName("clientRequestDateTime")
    val clientRequestDateTime: String? = null,

    @field:SerializedName("initiatorProduct")
    val initiatorProduct: String? = null,

    @field:SerializedName("initiatorSystem")
    val initiatorSystem: String? = null,

    @field:SerializedName("expiryDate")
    val expiryDate: String? = null,

    @field:SerializedName("words")
    val words: String,

    @field:SerializedName("version")
    val version: String,
)
