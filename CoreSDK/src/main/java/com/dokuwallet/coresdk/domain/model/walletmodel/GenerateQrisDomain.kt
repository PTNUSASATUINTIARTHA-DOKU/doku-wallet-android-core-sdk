package com.dokuwallet.coresdk.domain.model.walletmodel

data class GenerateQrisDomain(
    val qrCode: String? = null,
    val transactionId: String? = null,
    val responseCode: String? = null,
    val responseMessage: String? = null
)