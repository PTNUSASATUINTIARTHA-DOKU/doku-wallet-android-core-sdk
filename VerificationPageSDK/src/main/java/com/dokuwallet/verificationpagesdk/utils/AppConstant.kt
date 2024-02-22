package com.dokuwallet.verificationpagesdk.utils

import com.dokuwallet.coresdk.domain.model.vpmodel.ButtonPropertyDomain
import com.dokuwallet.coresdk.domain.model.vpmodel.ThemePropertyDomain

internal object AppConstant {
    const val CLIENT_ID = "VP-01001-1658977713105"
    const val PROJECT_ID = "VP-02002-1658978067285"
    const val SECRET = "FN0hSBMhch70nBW4lXKl"
    const val TOKEN_KEYWORD = "token="
    val DEFAULT_CLIENT_THEME = ThemePropertyDomain(
        "",
        "#FFFFFF",
        primaryButton = ButtonPropertyDomain(
            "#E1251B",
            "#E1251B",
            "#FFFFFF"
        ),
        secondaryButton = ButtonPropertyDomain(
            "#FFFFFF",
            "#E1251B",
            "#E1251B"
        )
    )
}