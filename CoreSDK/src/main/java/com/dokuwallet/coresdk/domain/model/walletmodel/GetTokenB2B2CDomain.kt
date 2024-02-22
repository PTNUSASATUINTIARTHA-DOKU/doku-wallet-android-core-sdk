package com.dokuwallet.coresdk.domain.model.walletmodel

data class GetTokenB2B2CDomain(
    val responseCode: String?,
    val responseMessage: String?,
    val accessToken: String?,
    val tokenType: String?,
    val accessTokenExpiryTime: String?,
    val refreshToken: String?,
    val refreshTokenExpiryTime: String?,
    val additionalInfo: String?
)
