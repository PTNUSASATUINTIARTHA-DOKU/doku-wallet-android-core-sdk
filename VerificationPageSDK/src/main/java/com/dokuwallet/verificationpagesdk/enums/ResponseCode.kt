package com.dokuwallet.verificationpagesdk.enums

internal enum class ResponseCode(val code: String) {
    RegisteredVerification("VP_0001"),
    UnregisteredVerification("VP_0002"),
    InvalidLandmark("VP_0007"),
    Spoofing("VP_0009"),
    FaceNotMatch("VP_0010"),
    MethodArgNotValid("VP_0011"),
    UnexpectedException("VP_0015")
}