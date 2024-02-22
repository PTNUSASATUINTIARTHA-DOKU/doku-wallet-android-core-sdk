package com.dokuwallet.coresdk.domain.model.walletmodel

data class GetTokenB2BDomain(
    val responseCode: String?,
    val responseMessage: String?,
    val accessToken: String?,
    val tokenType: String?,
    val expiresIn: String?,
    val additionalInfo: String?
)
