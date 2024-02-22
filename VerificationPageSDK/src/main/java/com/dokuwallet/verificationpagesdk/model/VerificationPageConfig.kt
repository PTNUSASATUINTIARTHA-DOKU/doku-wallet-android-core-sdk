package com.dokuwallet.verificationpagesdk.model

data class VerificationPageConfig(
    var clientId: String,
    var projectId: String,
    var customerId: String,
    var email: String,
    var phone: String,
    var secret: String,
    var hideKycFooter: Boolean
)