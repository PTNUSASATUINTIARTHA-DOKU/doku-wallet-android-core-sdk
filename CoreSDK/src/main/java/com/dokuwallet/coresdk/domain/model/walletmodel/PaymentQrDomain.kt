package com.dokuwallet.coresdk.domain.model.walletmodel


data class PaymentQrDomain(
    val responseCode: String? = null,
    val responseMessage: String? = null,
    val referenceNo: String? = null,
    val partnerReferenceNo: String? = null,
    val transactionDate: String? = null,
    val amount: AmountDomain? = null,
    val feeAmount: AmountDomain? = null,
    val additionalInfo: AdditionalInfoDomain? = null
)