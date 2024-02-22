package com.dokuwallet.coresdk.data.network.response.walletresponse

import com.google.gson.annotations.SerializedName

data class AccountBindingRes(
    @field:SerializedName("responseCode")
    val responseCode: String? = null,

    @field:SerializedName("responseMessage")
    val responseMessage: String? = null,

    @field:SerializedName("referenceNo")
    val referenceNo: String? = null,

    @field:SerializedName("partnerReferenceNo")
    val partnerReferenceNo: String? = null,

    @field:SerializedName("linkId")
    val linkId: String? = null,

    @field:SerializedName("nextAction")
    val nextAction: String? = null,

    @field:SerializedName("redirectUrl")
    val redirectUrl: String? = null,

    @field:SerializedName("userInfo")
    val userInfo: UserInfoRes? = null,
)