package com.danilovfa.organizer.tasks.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.danilovfa.organizer.tasks.data.mapper.toDomain
import com.danilovfa.organizer.tasks.domain.model.Task
import com.danilovfa.organizer.tasks.domain.repository.TasksRepository
import com.danilovfa.organizer.tasksDatabase.TasksDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope
import kotlinx.datetime.Clock
import kotlin.coroutines.CoroutineContext

class TasksRepositoryImpl(
    db: TasksDatabase,
    private val ioDispatcher: CoroutineContext = Dispatchers.IO
) : TasksRepository {

    private val queries = db.taskQueries

    override fun getTodaysTasks(): Flow<List<Task>> {
        return queries
            .getTodaysTasks()
            .asFlow()
            .mapToList(ioDispatcher)
            .map { taskEntities ->
                supervisorScope {
                    taskEntities
                        .map {
                            async { it.toDomain() }
                        }
                        .map { it.await() }
                }
            }
    }

    override suspend fun insertTask(title: String, durationInMinutes: Int) {
        queries.insertTask(
            id = null,
            title = title,
            createdAtEpoch = Clock.System.now().epochSeconds,
            durationMinutes = durationInMinutes.toLong()
        )
    }

    override suspend fun updateTask(task: Task) {
        queries.insertTask(
            id = task.id,
            title = task.title,
            createdAtEpoch = task.createdAtEpoch,
            durationMinutes = task.durationMinutes.toLong()
        )
    }

    override suspend fun deleteTask(id: Long) {
        queries.deleteTask(id)
    }

    override suspend fun deleteExpiredTasks() : Result<Unit> {
        return runCatching {
            queries.deleteExpiredTasks()
        }
    }
}