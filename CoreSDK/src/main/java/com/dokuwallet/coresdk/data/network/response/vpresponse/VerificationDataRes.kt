package com.dokuwallet.coresdk.data.network.response.vpresponse

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
internal data class VerificationDataRes(
    @field:SerializedName("link")
    val link: String? = null,

    @field:SerializedName("stage")
    val stage: Int
)