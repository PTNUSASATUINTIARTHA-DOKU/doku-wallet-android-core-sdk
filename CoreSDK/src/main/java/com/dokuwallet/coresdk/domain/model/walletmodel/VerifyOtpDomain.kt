package com.dokuwallet.coresdk.domain.model.walletmodel

data class VerifyOtpDomain(
    val responseCode: String? = null,
    val responseMessage: String? = null,
    val originalPartnerReferenceNo: String? = null,
    val originalReferenceNo: String? = null,
    val email: String? = null,
    val phoneNo: String? = null,
    val sendOtpFlag: String? = null,
    val additionalInfo: AdditionalInfoDomain? = null,
    val qparamsURL: String? = null,
    val qparams: QParamsDomain? = null
)