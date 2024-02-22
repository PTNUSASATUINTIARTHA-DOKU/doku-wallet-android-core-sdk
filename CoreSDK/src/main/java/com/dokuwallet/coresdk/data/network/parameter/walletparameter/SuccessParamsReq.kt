package com.dokuwallet.coresdk.data.network.parameter.walletparameter

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SuccessParamsReq(
    @field:SerializedName("accountId")
    val accountId: String,
)