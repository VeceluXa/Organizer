package com.danilovfa.organizer.tasks.domain.model

data class Task(
    val id: Long,
    val title: String,
    val createdAtEpoch: Long,
    val durationMinutes: Int
)
