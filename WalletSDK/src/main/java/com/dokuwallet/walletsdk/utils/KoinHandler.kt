package com.dokuwallet.walletsdk.utils

import android.content.Context
import com.dokuwallet.coresdk.di.CoreModule
import com.dokuwallet.walletsdk.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

object KoinHandler {
    fun initKoin(appContext: Context) {
        startKoin {
            androidLogger(Level.NONE)
            androidContext(appContext)

            val allModules = CoreModule.getAllModules().plus(viewModelModule).toList()

            modules(allModules)
        }
    }
}