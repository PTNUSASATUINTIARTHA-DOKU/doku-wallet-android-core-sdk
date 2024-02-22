package com.dokuwallet.coresdk.domain.model.walletmodel

data class SignOnDomain(
    val accessToken: String? = null,
    val expiresIn: Int? = null,
    val clientId: String? = null,
    val responseCode: String? = null,
    val responseMessage: String? = null
)
