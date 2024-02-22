package com.dokuwallet.coresdk.data.network.response.walletresponse

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
internal data class ErrorRes(
    @field:SerializedName("responseCode")
    val responseCode: String? = null,

    @field:SerializedName("responseMessage")
    val responseMessage: String? = null,
)
