package com.dokuwallet.dokusdk

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.dokuwallet.dokusdk.model.VpConfig
import com.dokuwallet.verificationpagesdk.model.VerificationPageConfig
import com.dokuwallet.verificationpagesdk.utils.VerificationPage

object DokuSDKHelper {
    object VpSDK {
        fun initConfig(context: Context, vpConfig: VpConfig) {
            val config = VerificationPageConfig(
                vpConfig.clientId,
                vpConfig.projectId,
                vpConfig.customerId,
                vpConfig.email,
                vpConfig.phone,
                vpConfig.secret,
                vpConfig.hideKycFooter
            )

            VerificationPage.initConfig(config, context)
        }

        fun startKyc(
            currentViewController: FragmentActivity,
            endViewController: FragmentActivity?
        ) {
            VerificationPage.SharedInstance.startKycFlow(currentViewController, endViewController)
        }

        fun start2FaRegister(
            currentViewController: FragmentActivity,
            endViewController: FragmentActivity?
        ) {
            VerificationPage.SharedInstance.start2FaRegisterFlow(
                currentViewController,
                endViewController
            )
        }

        fun start2FaAuth(
            currentViewController: FragmentActivity,
            endViewController: FragmentActivity?
        ) {
            VerificationPage.SharedInstance.start2FaAuthFlow(
                currentViewController,
                endViewController
            )
        }

        fun start2FaUpdate(
            currentViewController: FragmentActivity,
            endViewController: FragmentActivity?
        ) {
            VerificationPage.SharedInstance.start2FaUpdateFlow(
                currentViewController,
                endViewController
            )
        }
    }

//    object WalletSDK {
//        fun initConfig(context: Context, walletConfig: WalletConfig) {
//            val config = ServicePageConfig(
//                walletConfig.accountId,
//                walletConfig.clientSecret,
//                walletConfig.accountId,
//                walletConfig.sharedKey,
//                walletConfig.urlIntent,
//                walletConfig.transactionId,
//            )
//
//            WalletPage.initConfig(config, context)
//        }
//
//        fun startTransferP2P(
//            currentViewController: FragmentActivity,
//            endViewController: FragmentActivity?
//        ) {
//            WalletPage.SharedInstance.startTransferP2P(currentViewController, endViewController)
//        }
//
//        fun startTransferBank(
//            currentViewController: FragmentActivity,
//            endViewController: FragmentActivity?
//        ) {
//            WalletPage.SharedInstance.startTransferBank(currentViewController, endViewController)
//        }
//
//        fun startGenerateQris(
//            currentViewController: FragmentActivity,
//            endViewController: FragmentActivity?,
//            generateQrisParam: GenerateQrisParam
//        ) {
//            val generateQris = GenerateQris(
//                generateQrisParam.dpMallId,
//                generateQrisParam.terminalId,
//                generateQrisParam.postalCode,
//                generateQrisParam.amount,
//                generateQrisParam.feeType,
//                generateQrisParam.conveniencesFee,
//                generateQrisParam.uuid,
//                generateQrisParam.clientRequestDateTime,
//                generateQrisParam.initiatorProduct,
//                generateQrisParam.initiatorSystem,
//                generateQrisParam.expiryDate
//            )
//            WalletPage.SharedInstance.startGenerateQris(
//                currentViewController,
//                endViewController,
//                generateQris
//            )
//        }
//    }
}