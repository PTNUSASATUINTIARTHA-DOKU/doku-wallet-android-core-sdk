package com.dokuwallet.dokusdk.model

data class VpConfig(
    var clientId: String,
    var projectId: String,
    var customerId: String,
    var email: String,
    var phone: String,
    var secret: String,
    var hideKycFooter: Boolean
)