package com.dokuwallet.coresdk.domain.model.walletmodel

data class AccountBindingDomain(
    val responseCode: String? = null,
    val responseMessage: String? = null,
    val referenceNo: String? = null,
    val partnerReferenceNo: String? = null,
    val linkId: String? = null,
    val nextAction: String? = null,
    val redirectUrl: String? = null,
    val userInfo: UserInfoDomain? = null,
)