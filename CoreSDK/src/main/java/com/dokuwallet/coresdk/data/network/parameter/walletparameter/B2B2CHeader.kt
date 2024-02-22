package com.dokuwallet.coresdk.data.network.parameter.walletparameter

import androidx.annotation.Keep

@Keep
data class B2B2CHeader(
    val xTimestamp: String,
    val xPartnerId: String,
    val xExternalId: String,
    val xSignature: String,
    val authorization: String,
    val authorizationCustomer: String
)
