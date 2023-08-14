package com.danilovfa.organizer.tasks.data.mapper

import com.danilovfa.organizer.tasks.domain.model.Task
import tasksDatabase.TaskEntity

fun TaskEntity.toDomain() = Task(
    id = id,
    title = title,
    startedAt = startedAt,
    endedAt = endedAt
)