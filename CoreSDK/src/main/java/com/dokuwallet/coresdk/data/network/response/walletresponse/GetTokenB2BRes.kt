package com.dokuwallet.coresdk.data.network.response.walletresponse

import com.google.gson.annotations.SerializedName

data class GetTokenB2BRes(
    @field:SerializedName("responseCode")
    val responseCode: String? = null,

    @field:SerializedName("responseMessage")
    val responseMessage: String? = null,

    @field:SerializedName("accessToken")
    val accessToken: String? = null,

    @field:SerializedName("tokenType")
    val tokenType: String? = null,

    @field:SerializedName("expiresIn")
    val expiresIn: String? = null,

    @field:SerializedName("additionalInfo")
    val additionalInfo: String? = null
)
