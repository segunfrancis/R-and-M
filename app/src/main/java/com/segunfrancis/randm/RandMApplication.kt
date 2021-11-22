package com.segunfrancis.randm

import android.app.Application
import com.segunfrancis.randm.di.mainModule
import com.segunfrancis.randm.ui.detail.di.detailModule
import com.segunfrancis.randm.ui.home.di.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class RandMApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(androidContext = this@RandMApplication)
            androidLogger(level = Level.INFO)
            modules(
                mainModule,
                homeModule,
                detailModule
            )
        }
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
