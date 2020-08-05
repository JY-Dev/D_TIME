package com.jaeyoung.d_time.application

import android.app.Application
import com.jaeyoung.d_time.utils.DataProcess
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(listOf(module))
        }
    }

    val module = module {
        single {
            DataProcess(applicationContext)
        }
    }
}