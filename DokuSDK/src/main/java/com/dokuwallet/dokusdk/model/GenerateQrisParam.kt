package com.dokuwallet.dokusdk.model

data class GenerateQrisParam(
    val dpMallId: String,
    val terminalId: String,
    val postalCode: String,
    val amount: String? = null,
    val feeType: String? = null,
    val conveniencesFee: String? = null,
    val uuid: String? = null,
    val clientRequestDateTime: String? = null,
    val initiatorProduct: String? = null,
    val initiatorSystem: String? = null,
    val expiryDate: String? = null,
)
