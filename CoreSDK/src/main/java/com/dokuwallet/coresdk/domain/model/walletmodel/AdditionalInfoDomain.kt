package com.dokuwallet.coresdk.domain.model.walletmodel

data class AdditionalInfoDomain(
    val approvalCode: String? = null,
    val deviceId: String? = null,
    val linkId: String? = null,
    val authCode: String? = null,
    val remarks: String? = null,
    val merchantId: String? = null,
    val pointOfInitiationMethod: String? = null,
    val pointOfInitiationMethodDescription: String? = null,
    val feeType: String? = null,
    val feeTypeDescription: String? = null,
)