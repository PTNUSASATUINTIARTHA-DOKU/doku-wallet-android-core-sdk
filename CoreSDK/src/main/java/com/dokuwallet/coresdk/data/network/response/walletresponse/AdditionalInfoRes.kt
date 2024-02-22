package com.dokuwallet.coresdk.data.network.response.walletresponse

import com.google.gson.annotations.SerializedName

data class AdditionalInfoRes(
    @field:SerializedName("approvalCode")
    val approvalCode: String? = null,

    @field:SerializedName("deviceId")
    val deviceId: String? = null,

    @field:SerializedName("linkId")
    val linkId: String? = null,

    @field:SerializedName("authCode")
    val authCode: String? = null,

    @field:SerializedName("remarks")
    val remarks: String? = null,

    @field:SerializedName("merchantId")
    val merchantId: String? = null,

    @field:SerializedName("pointOfInitiationMethod")
    val pointOfInitiationMethod: String? = null,

    @field:SerializedName("pointOfInitiationMethodDescription")
    val pointOfInitiationMethodDescription: String? = null,

    @field:SerializedName("feeType")
    val feeType: String? = null,

    @field:SerializedName("feeTypeDescription")
    val feeTypeDescription: String? = null,
)
