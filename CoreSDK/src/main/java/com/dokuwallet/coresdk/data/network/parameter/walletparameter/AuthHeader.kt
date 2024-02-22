package com.dokuwallet.coresdk.data.network.parameter.walletparameter

import androidx.annotation.Keep

@Keep
data class AuthHeader(
    val xClientKey: String,
    val xTimestamp: String,
    val xSignature: String
)
