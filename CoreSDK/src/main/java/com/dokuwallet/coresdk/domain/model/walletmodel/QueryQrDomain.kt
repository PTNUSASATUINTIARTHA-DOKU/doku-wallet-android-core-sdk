package com.dokuwallet.coresdk.domain.model.walletmodel

data class QueryQrDomain(
    val responseCode: String? = null,
    val responseMessage: String? = null,
    val originalReferenceNo: String? = null,
    val originalPartnerReferenceNo: String? = null,
    val serviceCode: String? = null,
    val latestTransactionStatus: String? = null,
    val transactionStatusDesc: String? = null,
    val paidTime: String? = null,
    val amount: AmountDomain? = null,
    val feeAmount: AmountDomain? = null,
    val additionalInfo: AdditionalInfoDomain? = null,
)