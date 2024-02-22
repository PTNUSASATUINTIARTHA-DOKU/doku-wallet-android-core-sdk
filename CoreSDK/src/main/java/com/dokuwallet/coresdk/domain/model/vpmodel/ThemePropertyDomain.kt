package com.dokuwallet.coresdk.domain.model.vpmodel

data class ThemePropertyDomain(
    val logoLink: String,
    val backgroundColor: String,
    val primaryButton: ButtonPropertyDomain,
    val secondaryButton: ButtonPropertyDomain
)
