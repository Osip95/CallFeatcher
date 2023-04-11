package com.example.callfether

import android.app.Application
import com.example.callfether.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext

class App: Application() {
    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@App)
            modules(mainModule)
        }
    }
}