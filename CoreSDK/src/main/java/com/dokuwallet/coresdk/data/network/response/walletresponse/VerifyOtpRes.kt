package com.dokuwallet.coresdk.data.network.response.walletresponse

import com.google.gson.annotations.SerializedName

data class VerifyOtpRes(
    @field:SerializedName("responseCode")
    val responseCode: String? = null,

    @field:SerializedName("responseMessage")
    val responseMessage: String? = null,

    @field:SerializedName("originalPartnerReferenceNo")
    val originalPartnerReferenceNo: String? = null,

    @field:SerializedName("originalReferenceNo")
    val originalReferenceNo: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("phoneNo")
    val phoneNo: String? = null,

    @field:SerializedName("sendOtpFlag")
    val sendOtpFlag: String? = null,

    @field:SerializedName("additionalInfo")
    val additionalInfo: AdditionalInfoRes? = null,

    @field:SerializedName("qparamsURL")
    val qparamsURL: String? = null,

    @field:SerializedName("qparams")
    val qparams: QParamsRes? = null
)