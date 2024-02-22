package com.dokuwallet.coresdk.domain.model.walletmodel

import androidx.annotation.Keep

@Keep
data class CheckQrisDomain(
	val dpMallId: Int? = null,
	val transactionId: String? = null,
	val amount: Any? = null,
	val result: String? = null,
	val transactionDateTime: String? = null,
	val referenceId: String? = null,
	val clientId: String? = null,
	val responseCode: String? = null,
	val responseMessage: String? = null
)
