package com.dokuwallet.coresdk.data.network.response.vpresponse

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
internal data class CaptureIdDataRes(
    @field:SerializedName("url")
    val url: String
)
