package com.danilovfa.organizer.tasks.ui.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp

@Immutable
data class TaskState(
    val id: Long,
    val title: String,
    val createdAtEpoch: Long,
    val containerHeight: Dp,
    val durationInMinutes: Int,
)