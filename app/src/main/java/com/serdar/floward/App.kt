package com.serdar.floward

import android.app.Application
import com.serdar.floward.modules.*
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                networkModule,
                apiModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}