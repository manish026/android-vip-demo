package com.marketpulse.sniper.myapplication

import android.app.Application
import org.koin.core.context.GlobalContext.startKoin

class TodoApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule)
        }
    }
    
}