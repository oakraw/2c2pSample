package com.oakraw.a2c2psample

import android.app.Application
import com.ccpp.pgw.sdk.android.builder.PGWSDKParamsBuilder
import com.ccpp.pgw.sdk.android.core.PGWSDK
import com.ccpp.pgw.sdk.android.enums.APIEnvironment
import com.segment.analytics.kotlin.android.Analytics
import com.segment.analytics.kotlin.core.Analytics

class AppController : Application() {
    var analytics: Analytics? = null

    override fun onCreate() {
        super.onCreate()
        val pgwsdkParams = PGWSDKParamsBuilder(this, APIEnvironment.Sandbox).build()
        PGWSDK.initialize(pgwsdkParams)

        analytics = Analytics(BuildConfig.SEGMENT_WRITE_KEY, this) {
            this.collectDeviceId = true
            this.trackApplicationLifecycleEvents = true
            this.trackDeepLinks = true
            this.flushAt = 3
            this.flushInterval = 10
        }
    }
}
