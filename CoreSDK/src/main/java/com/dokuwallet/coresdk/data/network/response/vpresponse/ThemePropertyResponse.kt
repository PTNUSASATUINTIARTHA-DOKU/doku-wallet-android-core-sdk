package com.dokuwallet.coresdk.data.network.response.vpresponse

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
internal data class ThemePropertyResponse(
    @field:SerializedName("logoLink")
    var logoLink: String,

    @field:SerializedName("backgroundColor")
    var backgroundColor: String,

    @field:SerializedName("primaryButton")
    var primaryButton: ButtonPropertyRes,

    @field:SerializedName("secondaryButton")
    var secondaryButton: ButtonPropertyRes
)
