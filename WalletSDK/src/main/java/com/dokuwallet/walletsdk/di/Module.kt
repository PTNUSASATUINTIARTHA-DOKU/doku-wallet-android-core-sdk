package com.dokuwallet.walletsdk.di

import com.dokuwallet.walletsdk.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// TODO EXT: Don't Forget to add new ViewModel
internal val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
}