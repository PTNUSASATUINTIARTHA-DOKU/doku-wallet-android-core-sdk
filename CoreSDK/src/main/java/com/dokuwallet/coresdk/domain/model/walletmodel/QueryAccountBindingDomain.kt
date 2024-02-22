package com.dokuwallet.coresdk.domain.model.walletmodel

data class QueryAccountBindingDomain(
    val responseCode: String? = null,
    val responseMessage: String? = null,
    val partnerReferenceNo: String? = null,
    val additionalInfo: AdditionalInfoDomain? = null,
)