package com.dokuwallet.verificationpagesdk.utils

import android.content.Context
import com.dokuwallet.coresdk.utils.BasePreferenceManager
import com.dokuwallet.verificationpagesdk.enums.VerificationType

internal class PreferenceManager(context: Context) : BasePreferenceManager(PREF_NAME, context) {
    fun clearUserData() {
        editor.clear()
        editor.commit()
    }

    fun putVerificationToken(token: String) {
        when (VerificationPage.SharedInstance.verificationType) {
            VerificationType.KYC -> encryptPreference(KYC_TOKEN, token)
            VerificationType.PAY_V -> encryptPreference(PAYV_TOKEN, token)
            VerificationType.TWO_FA_REG -> encryptPreference(TWO_FA_REG_TOKEN, token)
            else -> encryptPreference(TOKEN, token)
        }
    }

    fun getVerificationToken(): String? {
        return when (VerificationPage.SharedInstance.verificationType) {
            VerificationType.KYC -> decryptPreference(KYC_TOKEN)
            VerificationType.PAY_V -> decryptPreference(PAYV_TOKEN)
            VerificationType.TWO_FA_REG -> decryptPreference(TWO_FA_REG_TOKEN)
            else -> decryptPreference(TOKEN)
        }
    }

    fun putVerificationStage(stage: Int) {
        encryptPreference(STAGE, stage.toString())
    }

    companion object {
        private const val PREF_NAME = "vppref"
        private const val TOKEN = "DOKUv3RiF1C4tionP4g3Token"
        private const val KYC_TOKEN = "DOKUv3RiF1C4tionP4g3KycToken"
        private const val PAYV_TOKEN = "DOKUv3RiF1C4tionP4g3PayVerificationToken"
        private const val TWO_FA_REG_TOKEN = "DOKUv3RiF1C4tionP4g3TwoFaRegisterToken"
        private const val STAGE = "stage"
    }
}