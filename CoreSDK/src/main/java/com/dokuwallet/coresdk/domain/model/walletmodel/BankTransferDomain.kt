package com.dokuwallet.coresdk.domain.model.walletmodel

data class BankTransferDomain(
    val responseCode: String? = null,
    val responseMessage: String? = null,
    val referenceNo: String? = null,
    val partnerReferenceNo: String? = null,
    val transactionDate: String? = null,
    val referenceNumber: String? = null,
)