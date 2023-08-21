package com.danilovfa.organizer.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.danilovfa.organizer.core.presentation.OrganizerTheme
import com.danilovfa.organizer.ui.root.RootComponent
import com.danilovfa.organizer.ui.root.RootContent
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startUpdateWorker()

        val rootComponent = RootComponent(
            componentContext = defaultComponentContext(),
            storeFactory = DefaultStoreFactory()
        )

        setContent {
            OrganizerTheme {
                RootContent(rootComponent)
            }
        }
    }

    private fun startUpdateWorker() {
        val work = PeriodicWorkRequestBuilder<DataUpdateWorker>(
            DataUpdateWorker.REPEAT_TIME_INTERVAL_HOURS,
            TimeUnit.HOURS
        ).build()

        val workManager = WorkManager.getInstance(this)
        workManager.enqueueUniquePeriodicWork(
            DataUpdateWorker.TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            work
        )
    }
}