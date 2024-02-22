package com.dokuwallet.dokusdk.model

data class WalletConfig(
    val clientId: String,
    val clientSecret: String,
    val accountId: String,
    val sharedKey: String,
    val urlIntent: String,
    val transactionId: String? = null
)