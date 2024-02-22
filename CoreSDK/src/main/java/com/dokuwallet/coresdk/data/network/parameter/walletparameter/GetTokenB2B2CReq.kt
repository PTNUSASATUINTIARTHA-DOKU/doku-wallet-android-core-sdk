package com.dokuwallet.coresdk.data.network.parameter.walletparameter

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GetTokenB2B2CReq(
    @field:SerializedName("grantType")
    val grantType: String,

    @field:SerializedName("authCode")
    val authCode: String
)
