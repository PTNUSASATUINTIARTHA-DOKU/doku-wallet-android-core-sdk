package com.dokuwallet.coresdk.data.network.response.walletresponse

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
internal data class ResponseMessageRes(
    @field:SerializedName("en")
    val en: String? = null,

    @field:SerializedName("id")
    val id: String? = null
)