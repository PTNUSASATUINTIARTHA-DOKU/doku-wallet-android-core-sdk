package com.dokuwallet.walletsdk

import android.app.Application
import com.dokuwallet.coresdk.utils.BasePreferenceManager
import com.dokuwallet.walletsdk.utils.KoinHandler

internal class WalletBaseApp : Application() {
    override fun onCreate() {
        super.onCreate()

        BasePreferenceManager.generateKey()
        KoinHandler.initKoin(this)
    }
}