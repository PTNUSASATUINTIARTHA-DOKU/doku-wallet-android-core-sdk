package com.dokuwallet.coresdk.data.network.parameter.walletparameter

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GetTokenB2BReq(
    @field:SerializedName("grantType")
    val grantType: String
)
