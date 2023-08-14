package com.danilovfa.organizer.tasks.ui.model

import androidx.compose.runtime.Immutable

@Immutable
data class TaskState(
    val id: Long?,
    val title: String,
    val startedAt: Long,
    val endedAt: Long
)