package com.dokuwallet.verificationpagesdk.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.dokuwallet.verificationpagesdk.R
import com.dokuwallet.verificationpagesdk.databinding.VpSdkActivityHomeBinding
import com.dokuwallet.verificationpagesdk.enums.ResponseCode
import com.dokuwallet.verificationpagesdk.enums.VerificationType
import com.dokuwallet.verificationpagesdk.utils.VPUtils
import com.dokuwallet.verificationpagesdk.utils.VerificationPage
import com.dokuwallet.coresdk.R as coreResource

internal class VpSDKHomeActivity : AppCompatActivity() {
    private lateinit var binding: VpSdkActivityHomeBinding

    private var currentDestination: Int? = null
    var isBackPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        binding = VpSdkActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.mainToolbar)
        binding.btnBack.setOnClickListener { onBackPressed() }
        destinationController()
    }

    override fun onBackPressed() {
        val navController = Navigation.findNavController(this, R.id.fragment_container)
        when (navController.currentDestination?.id) {
            navController.graph.startDestinationId -> super.onBackPressed()
            R.id.guideLivenessFragment -> finish()
            R.id.livenessFinishFragment -> {
                VPUtils.intentToEndView(this, true)
            }
            else -> {
                isBackPressed = true
                navController.navigateUp()
            }
        }
        return
    }

    private fun destinationController() {
        binding.apply {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
            val navController = navHostFragment.navController
            val navGraph = navController.navInflater.inflate(R.navigation.vp_sdk_main_nav)

            // Mapping based on stage
            navGraph.setStartDestination(
                when (VerificationPage.SharedInstance.verificationType) {
                    VerificationType.KYC -> {
                        when (VerificationPage.stage) {
                            1 -> R.id.ktpFragment
                            2 -> R.id.ktpFragment
                            3 -> R.id.selfieFragment
                            4 -> R.id.identityFragment
                            5 -> R.id.guideLivenessFragment
                            6 -> R.id.guideLivenessFragment
                            else -> R.id.ktpFragment
                        }
                    }
                    VerificationType.PAY_V -> R.id.ktpFragment
                    VerificationType.TWO_FA_REG -> R.id.livenessFragment
                    VerificationType.TWO_FA_AUTH -> R.id.guideLivenessFragment
                    VerificationType.TWO_FA_UPDATE -> R.id.livenessFragment
                    else -> R.id.ktpFragment
                }
            )

            navController.graph = navGraph

            intent.getStringExtra(ERROR_CODE_KEY).also {
                if (it == ResponseCode.UnexpectedException.code) {
                    navController.navigate(R.id.livenessFinishFragment, Bundle().apply {
                        putString("errorCode", it)
                    })
                }
            }

            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.guideLivenessFragment -> {
                        toolbarTitle.text =
                            when (VerificationPage.SharedInstance.verificationType) {
                                VerificationType.KYC -> getString(R.string.verifikasi_wajah)
                                VerificationType.TWO_FA_REG -> getString(R.string.registrasi_biometrik)
                                VerificationType.TWO_FA_AUTH -> getString(R.string.autentikasi_biometrik)
                                VerificationType.TWO_FA_UPDATE -> getString(R.string.update_biometrik)
                                else -> getString(R.string.verifikasi_wajah)
                            }
                        btnBack.setImageDrawable(
                            ContextCompat.getDrawable(
                                this@VpSDKHomeActivity,
                                coreResource.drawable.ic_baseline_close_24
                            )
                        )
                    }
                    else -> {
                        btnBack.setImageDrawable(
                            ContextCompat.getDrawable(
                                this@VpSDKHomeActivity,
                                coreResource.drawable.ic_baseline_arrow_back_18
                            )
                        )
                    }
                }
                if (!isBackPressed) currentDestination = destination.id
                if (currentDestination == destination.id) isBackPressed = false
            }
        }
    }

    override fun onDestroy() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        super.onDestroy()
        getPreferences(Context.MODE_PRIVATE)
            .edit()
            .clear()
            .apply()
    }

    companion object {
        const val ERROR_CODE_KEY = "error_code_key"
    }
}