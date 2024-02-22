package com.dokuwallet.verificationpagesdk.utils

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.dokuwallet.coresdk.domain.model.vpmodel.ButtonPropertyDomain
import com.dokuwallet.coresdk.domain.model.vpmodel.ThemePropertyDomain
import com.dokuwallet.coresdk.utils.CommonUtils.convertColorCode
import com.dokuwallet.verificationpagesdk.R
import com.dokuwallet.verificationpagesdk.di.viewModelModule
import com.dokuwallet.verificationpagesdk.enums.ResponseCode
import com.dokuwallet.verificationpagesdk.enums.VerificationType
import com.dokuwallet.verificationpagesdk.enums.VerificationType.KYC
import com.dokuwallet.verificationpagesdk.enums.VerificationType.TWO_FA_AUTH
import com.dokuwallet.verificationpagesdk.enums.VerificationType.TWO_FA_REG
import com.dokuwallet.verificationpagesdk.enums.VerificationType.TWO_FA_UPDATE
import com.dokuwallet.verificationpagesdk.model.VerificationPageConfig
import com.dokuwallet.verificationpagesdk.ui.HomeViewModel
import com.dokuwallet.verificationpagesdk.ui.VpSDKHomeActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

object VerificationPage {
    lateinit var verificationPageConfig: VerificationPageConfig
    internal var stage: Int? = null
    internal var clientTheme: ThemePropertyDomain = AppConstant.DEFAULT_CLIENT_THEME

    fun initConfig(verificationPageConfig: VerificationPageConfig, context: Context) {
        val prefManager: PreferenceManager by lazy {
            PreferenceManager(context)
        }
        prefManager.clearUserData()
        this.verificationPageConfig = verificationPageConfig
    }

    object SharedInstance {
        internal var rootFragment: FragmentActivity? = null
        internal var endViewFragment: FragmentActivity? = null
        internal var verificationType: VerificationType? = null
        internal var VP_STATUS_VALUE = false

        fun getStatusValue() = VP_STATUS_VALUE

        fun startKycFlow(
            currentViewController: FragmentActivity,
            endViewController: FragmentActivity?
        ) {
            verificationType = KYC
            startFlow(currentViewController, endViewController)
        }

        fun start2FaRegisterFlow(
            currentViewController: FragmentActivity,
            endViewController: FragmentActivity?
        ) {
            verificationType = TWO_FA_REG
            startFlow(currentViewController, endViewController)
        }

        fun start2FaAuthFlow(
            currentViewController: FragmentActivity,
            endViewController: FragmentActivity?
        ) {
            verificationType = TWO_FA_AUTH
            startFlow(currentViewController, endViewController)
        }

        fun start2FaUpdateFlow(
            currentViewController: FragmentActivity,
            endViewController: FragmentActivity?
        ) {
            verificationType = TWO_FA_UPDATE
            startFlow(currentViewController, endViewController)
        }

        internal fun restartFlow() {
            when (verificationType) {
                KYC -> startKycFlow(rootFragment!!, endViewFragment)
                TWO_FA_REG -> start2FaRegisterFlow(rootFragment!!, endViewFragment)
                TWO_FA_AUTH -> start2FaAuthFlow(rootFragment!!, endViewFragment)
                TWO_FA_UPDATE -> start2FaUpdateFlow(rootFragment!!, endViewFragment)
                else -> {}
            }
        }

        private fun startFlow(
            currentViewController: FragmentActivity,
            endViewController: FragmentActivity?
        ) {
            VP_STATUS_VALUE = false
            setupFeatureRequest(currentViewController, endViewController)

            val viewModel: HomeViewModel by currentViewController.viewModel()
            loadKoinModules(viewModelModule)
            val bottomSheet: BottomSheetDialog by lazy {
                BottomSheetDialog(currentViewController)
            }
            val indicatorView: Dialog? = null
            val resultHandler by lazy {
                ResultHandler(currentViewController, indicatorView, bottomSheet)
            }
            val prefManager: PreferenceManager by lazy {
                PreferenceManager(currentViewController)
            }

            resultHandler.isLoading(true)

            fun getClientTheme(token: String) {
                viewModel.getClientTheme(token).observe(currentViewController) { result ->
                    resultHandler.handle(result) {
                        resultHandler.isLoading(false)
                        result.data?.data?.let {
                            clientTheme = ThemePropertyDomain(
                                logoLink = it.logoLink,
                                backgroundColor = convertColorCode(it.backgroundColor),
                                primaryButton = ButtonPropertyDomain(
                                    background = convertColorCode(it.primaryButton.background),
                                    border = convertColorCode(it.primaryButton.border),
                                    color = convertColorCode(it.primaryButton.color)
                                ),
                                secondaryButton = ButtonPropertyDomain(
                                    background = convertColorCode(it.secondaryButton.background),
                                    border = convertColorCode(it.secondaryButton.border),
                                    color = convertColorCode(it.secondaryButton.color)
                                ),
                            )
                        }
                        Intent(
                            currentViewController.applicationContext,
                            VpSDKHomeActivity::class.java
                        ).also {
                            currentViewController.startActivity(it)
                        }
                    }
                }
            }

            fun requestVerification() {
                val listHandleError = listOf(
                    ResponseCode.RegisteredVerification,
                    ResponseCode.UnregisteredVerification
                )
                viewModel.requestVerification().observe(currentViewController) { result ->
                    resultHandler.handle(result, listHandleError, handleError = {
                        when (it?.code) {
                            ResponseCode.RegisteredVerification.code -> {
                                VPUtils.generalBottomSheet(
                                    builder = bottomSheet,
                                    title = currentViewController.getString(R.string.already_register_title),
                                    caption = currentViewController.getString(R.string.already_register_caption),
                                    btnCancelTitle = currentViewController.getString(R.string.enter),
                                    onCancelClick = {
                                        start2FaAuthFlow(
                                            rootFragment!!,
                                            endViewFragment
                                        )
                                    }
                                )
                            }
                            ResponseCode.UnregisteredVerification.code -> {
                                VPUtils.generalBottomSheet(
                                    builder = bottomSheet,
                                    title = currentViewController.getString(R.string.not_registered_title),
                                    caption = currentViewController.getString(R.string.not_registered_caption),
                                    btnCancelTitle = currentViewController.getString(R.string.close),
                                    btnOkTitle = currentViewController.getString(R.string.verification)
                                ) {
                                    start2FaRegisterFlow(
                                        rootFragment!!,
                                        endViewFragment
                                    )
                                }
                            }
                            else -> return@handle
                        }
                    }) {
                        if (result.data == null) {
                            return@handle
                        }

                        val data = result.data!!

                        if (data.result != "SUCCESS") {
                            VPUtils.generalBottomSheet(
                                builder = bottomSheet,
                                title = currentViewController.getString(com.dokuwallet.coresdk.R.string.general_alert_title),
                                caption = data.message.toString(),
                                btnOkTitle = currentViewController.getString(com.dokuwallet.coresdk.R.string.ok_title)
                            )
                        } else {
                            val tokenKeyStartIndex =
                                data.data?.link?.indexOf(AppConstant.TOKEN_KEYWORD)
                                    ?.plus(AppConstant.TOKEN_KEYWORD.length)
                            val token = tokenKeyStartIndex?.let {
                                data.data?.link?.subSequence(
                                    it,
                                    data.data?.link?.length ?: 0
                                )
                            }
                            prefManager.putVerificationToken(token.toString())
                            data.data?.stage?.let { prefManager.putVerificationStage(it) }
                            stage = data.data?.stage
                            if (stage == 6) {
                                VPUtils.intentToEndView(currentViewController, true)
                            } else {
                                getClientTheme(token.toString())
                            }
                        }
                    }
                }
            }

            fun validateToken(token: String) {
                viewModel.validateToken(token).observe(currentViewController) { result ->
                    resultHandler.handle(result) {
                        if (result.data == null) {
                            return@handle
                        }

                        if (result.data!!.result != "SUCCESS") {
                            requestVerification()
                        } else {
                            stage = result.data!!.data?.stage
                            getClientTheme(token)
                        }
                    }
                }
            }

            requestVerification()
        }

        fun setupFeatureRequest(
            currentViewController: FragmentActivity,
            endViewController: FragmentActivity?
        ) {
            this.rootFragment = currentViewController
            this.endViewFragment = endViewController
        }

        fun resetFeatureRequest() {
            rootFragment = null
            endViewFragment = null
            verificationType = null
        }
    }
}