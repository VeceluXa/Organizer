package com.danilovfa.organizer.tasks.ui.mapper

import com.danilovfa.organizer.tasks.domain.model.Task
import com.danilovfa.organizer.tasks.ui.model.TaskState

fun Task.toState(): TaskState {
    return TaskState(
        id = id,
        title = title,
        startedAt = startedAt,
        endedAt = endedAt
    )
}

fun TaskState.toDomain(): Task {
    return Task(
        id = id,
        title = title,
        startedAt = startedAt,
        endedAt = endedAt
    )
}