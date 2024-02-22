package com.dokuwallet.coresdk.domain.model.walletmodel

data class BankInquiryDomain(
    val responseCode: String? = null,
    val responseMessage: String? = null,
    val referenceNo: String? = null,
    val partnerReferenceNo: String? = null,
    val beneficiaryAccountNumber: String? = null,
    val beneficiaryAccountName: String? = null,
    val beneficiaryBankCode: String? = null,
    val amount: AmountDomain? = null,
)