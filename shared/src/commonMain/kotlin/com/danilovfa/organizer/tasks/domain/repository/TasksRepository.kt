package com.danilovfa.organizer.tasks.domain.repository

import com.danilovfa.organizer.tasks.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    fun getTodaysTasks(): Flow<List<Task>>
    suspend fun insertTask(title: String, durationInMinutes: Int)
    suspend fun updateTask(task: Task)

    suspend fun deleteTask(id: Long)
    suspend fun deleteExpiredTasks() : Result<Unit>
}