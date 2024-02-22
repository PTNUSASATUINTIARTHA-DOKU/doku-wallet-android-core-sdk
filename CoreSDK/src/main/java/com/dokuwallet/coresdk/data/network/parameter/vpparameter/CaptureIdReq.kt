package com.dokuwallet.coresdk.data.network.parameter.vpparameter

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CaptureIdReq(
    @field:SerializedName("images")
    val images: ArrayList<String>
)
