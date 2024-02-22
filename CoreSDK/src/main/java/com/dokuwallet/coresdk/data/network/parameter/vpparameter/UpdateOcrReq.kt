package com.dokuwallet.coresdk.data.network.parameter.vpparameter

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UpdateOcrReq(
    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("gender")
    val gender: String? = null,

    @field:SerializedName("district")
    val district: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("idNumber")
    val idNumber: String? = null,

    @field:SerializedName("subDistrict")
    val subDistrict: String? = null,

    @field:SerializedName("religion")
    val religion: String? = null
)
