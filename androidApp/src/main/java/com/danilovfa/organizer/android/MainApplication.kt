package com.danilovfa.organizer.android

import android.app.Application
import com.danilovfa.organizer.core.di.androidModule
import com.danilovfa.organizer.tasks.di.tasksModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            androidLogger()
            modules(androidModule() + tasksModule())
        }
    }
}