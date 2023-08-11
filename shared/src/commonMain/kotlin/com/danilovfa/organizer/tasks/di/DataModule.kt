package com.danilovfa.organizer.tasks.di

import com.danilovfa.organizer.tasks.data.repository.BannerRepositoryImpl
import com.danilovfa.organizer.tasks.data.repository.TasksRepositoryImpl
import com.danilovfa.organizer.tasks.domain.repository.BannerRepository
import com.danilovfa.organizer.tasks.domain.repository.TasksRepository
import org.koin.dsl.module

val dataModule = module {
    single<TasksRepository> {
        TasksRepositoryImpl(
            db = get()
        )
    }

    single<BannerRepository> {
        BannerRepositoryImpl(
            fileManager = get()
        )
    }
}