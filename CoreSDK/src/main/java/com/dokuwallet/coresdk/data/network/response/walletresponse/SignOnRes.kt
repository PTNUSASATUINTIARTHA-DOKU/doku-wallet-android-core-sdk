package com.dokuwallet.coresdk.data.network.response.walletresponse

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
internal data class SignOnRes(
    @field:SerializedName("accessToken")
    val accessToken: String? = null,

    @field:SerializedName("expiresIn")
    val expiresIn: Int? = null,

    @field:SerializedName("clientId")
    val clientId: String? = null,

    @field:SerializedName("responseCode")
    val responseCode: String? = null,

    @field:SerializedName("responseMessage")
    val responseMessage: ResponseMessageRes? = null
)