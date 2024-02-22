package com.dokuwallet.coresdk.data.network.response.vpresponse

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
internal data class ButtonPropertyRes(
    @field:SerializedName("background")
    var background: String,

    @field:SerializedName("border")
    var border: String,

    @field:SerializedName("color")
    var color: String
)