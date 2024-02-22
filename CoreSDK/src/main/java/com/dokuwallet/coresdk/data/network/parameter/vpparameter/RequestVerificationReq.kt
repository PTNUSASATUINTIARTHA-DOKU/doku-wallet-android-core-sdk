package com.dokuwallet.coresdk.data.network.parameter.vpparameter

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RequestVerificationReq(
    @field:SerializedName("projectId")
    val projectId: String,

    @field:SerializedName("customerId")
    val customerId: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("phone")
    val phone: String,

    @field:SerializedName("verificationType")
    val verificationType: String,

    @field:SerializedName("integrationMethod")
    val integrationMethod: String
)