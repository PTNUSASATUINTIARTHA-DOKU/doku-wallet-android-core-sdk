package com.dokuwallet.coresdk.domain.model.walletmodel

data class GenerateQrDomain(
    val responseCode: String?,
    val responseMessage: String?,
    val referenceNo: String?,
    val partnerReferenceNo: String?,
    val qrContent: String?,
    val terminalId: String?
)