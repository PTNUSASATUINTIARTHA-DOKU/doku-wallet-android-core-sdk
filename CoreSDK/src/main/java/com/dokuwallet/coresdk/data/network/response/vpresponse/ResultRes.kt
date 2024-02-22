package com.dokuwallet.coresdk.data.network.response.vpresponse

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
internal data class ResultRes<T>(
    @field:SerializedName("result")
    val result: String,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("data")
    val data: T? = null,

    @field:SerializedName("dateTime")
    val dateTime: String,

    @field:SerializedName("errorCode")
    val errorCode: String? = null,

    @field:SerializedName("errorDesc")
    val errorDesc: String? = null,

    @field:SerializedName("responseCode")
    val responseCode: String? = null
)
