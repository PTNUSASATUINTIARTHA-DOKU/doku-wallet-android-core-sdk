package com.dokuwallet.coresdk.utils

object WalletEndPoint {
    const val getTokenB2B = "authorization/v1/access-token/b2b"
    const val getTokenB2B2C = "authorization/v1/access-token/b2b2c"
    const val generateQr = "snap-adapter/b2b/v1.0/qr/qr-mpm-generate"
    const val queryQr = "snap-adapter/b2b/v1.0/qr/qr-mpm-query"
    const val accountCreation = "snap-adapter/b2b/v1.0/registration-account-creation"
    const val verifyOtp = "snap-adapter/b2b/v1.0/otp-verification"
    const val accountBinding = "snap-adapter/b2b/v1.0/registration-account-binding"
    const val queryAccountBinding = "snap-adapter/b2b/v1.0/registration-account-inquiry"
    const val bankInquiry = "snap-adapter/b2b/v1.0/emoney/bank-account-inquiry"
    const val bankTransfer = "snap-adapter/b2b2c/v1.0/emoney/transfer-bank"
    const val decodeQr = "snap-adapter/b2b/v1.0/qr/qr-mpm-decode"
    const val paymentQr = "snap-adapter/b2b2c/v1.0/qr/qr-mpm-payment"
}