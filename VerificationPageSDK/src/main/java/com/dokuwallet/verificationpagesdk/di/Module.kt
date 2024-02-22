package com.dokuwallet.verificationpagesdk.di

import com.dokuwallet.verificationpagesdk.ui.HomeViewModel
import com.dokuwallet.verificationpagesdk.ui.identity.IdentityViewModel
import com.dokuwallet.verificationpagesdk.ui.ktp.KtpViewModel
import com.dokuwallet.verificationpagesdk.ui.liveness.LivenessViewModel
import com.dokuwallet.verificationpagesdk.ui.selfie.SelfieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// TODO EXT: Don't Forget to add new ViewModel
internal val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { KtpViewModel(get()) }
    viewModel { SelfieViewModel(get()) }
    viewModel { LivenessViewModel(get()) }
    viewModel { IdentityViewModel(get()) }
}