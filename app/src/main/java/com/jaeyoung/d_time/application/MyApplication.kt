package com.jaeyoung.d_time.application

import android.app.Application
import com.jaeyoung.d_time.utils.CameraUtil
import com.jaeyoung.d_time.utils.dataprocess.DataProcessBookMark
import com.jaeyoung.d_time.utils.dataprocess.DataProcessDiary
import com.jaeyoung.d_time.utils.dataprocess.DataProcessTimeTable
import com.jaeyoung.d_time.utils.dataprocess.DataProcessTodo
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
            DataProcessTodo(
                applicationContext
            )
        }
        single {
            DataProcessDiary(
                applicationContext
            )
        }
        single {
            DataProcessTimeTable(
                applicationContext
            )
        }
        single {
            CameraUtil(applicationContext)
        }
        single {
            DataProcessBookMark(
                applicationContext
            )
        }
    }
}