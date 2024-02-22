package com.dokuwallet.coresdk.domain.model.walletmodel

data class DecodeQrDomain(
    val responseCode: String? = null,
    val responseMessage: String? = null,
    val referenceNo: String? = null,
    val partnerReferenceNo: String? = null,
    val merchantName: String? = null,
    val merchantCategory: String? = null,
    val merchantLocation: String? = null,
    val transactionAmount: AmountDomain? = null,
    val feeAmount: AmountDomain? = null,
    val additionalInfo: AdditionalInfoDomain? = null
)