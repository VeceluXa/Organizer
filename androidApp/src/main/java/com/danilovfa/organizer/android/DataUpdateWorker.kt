package com.danilovfa.organizer.android

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.danilovfa.organizer.tasks.domain.repository.BannerRepository
import com.danilovfa.organizer.tasks.domain.repository.TasksRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class DataUpdateWorker(
    context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams), KoinComponent {
    override suspend fun doWork(): Result = coroutineScope {

        val bannerRepository: BannerRepository = get()
        val tasksRepository: TasksRepository = get()

        val tasks = listOf(tasksRepository::deleteExpiredTasks, bannerRepository::updateBanners)
        val jobs = List(tasks.size) {
            async { tasks[it]() }
        }

        jobs.awaitAll().forEach { taskResult ->
            if (taskResult.isFailure) {
                return@coroutineScope Result.failure()
            }
        }

        return@coroutineScope Result.success()
    }

    companion object {
        const val REPEAT_TIME_INTERVAL_HOURS = 24L
        const val TAG = "DataUpdateWorker"
    }
}