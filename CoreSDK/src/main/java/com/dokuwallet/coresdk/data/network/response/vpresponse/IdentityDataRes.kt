package com.dokuwallet.coresdk.data.network.response.vpresponse

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
internal data class IdentityDataRes(
    @field:SerializedName("idNumber")
    val idNumber: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("religion")
    val religion: String? = null,

    @field:SerializedName("gender")
    val gender: String? = null,

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("bloodType")
    val bloodType: String? = null,

    @field:SerializedName("district")
    val district: String? = null,
)
