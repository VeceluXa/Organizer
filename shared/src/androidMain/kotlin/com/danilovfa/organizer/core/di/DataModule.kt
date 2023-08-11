package com.danilovfa.organizer.core.di

import com.danilovfa.organizer.core.data.DatabaseDriverFactory
import com.danilovfa.organizer.core.data.FileManager
import com.danilovfa.organizer.tasksDatabase.TasksDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    single {
        DatabaseDriverFactory(androidContext()).create()
    }

    single {
        TasksDatabase(driver = get())
    }

    single {
        FileManager(
            context = androidContext()
        )
    }
}