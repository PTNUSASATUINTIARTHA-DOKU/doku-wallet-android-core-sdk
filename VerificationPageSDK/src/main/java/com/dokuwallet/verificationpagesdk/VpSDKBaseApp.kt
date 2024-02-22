package com.dokuwallet.verificationpagesdk

import android.app.Application
import com.dokuwallet.coresdk.utils.BasePreferenceManager
import com.dokuwallet.verificationpagesdk.utils.KoinHandler

internal class VpSDKBaseApp : Application() {
    override fun onCreate() {
        super.onCreate()

        BasePreferenceManager.generateKey()
        KoinHandler.initKoin(this)
    }
}