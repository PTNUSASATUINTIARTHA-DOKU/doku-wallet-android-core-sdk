package com.dokuwallet.coresdk.data.network.response.walletresponse

import com.google.gson.annotations.SerializedName

data class AccountCreationRes(
    @field:SerializedName("responseCode")
    val responseCode: String? = null,

    @field:SerializedName("responseMessage")
    val responseMessage: String? = null,

    @field:SerializedName("referenceNo")
    val referenceNo: String? = null,

    @field:SerializedName("partnerReferenceNo")
    val partnerReferenceNo: String? = null,
)
