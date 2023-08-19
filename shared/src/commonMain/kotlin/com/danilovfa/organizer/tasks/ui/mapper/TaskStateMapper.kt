package com.danilovfa.organizer.tasks.ui.mapper

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.danilovfa.organizer.tasks.domain.model.Task
import com.danilovfa.organizer.tasks.ui.model.TaskState

fun Task.toState(): TaskState {
    return TaskState(
        id = id,
        title = title,
        createdAtEpoch = createdAtEpoch,
        durationInMinutes = durationMinutes,
        containerHeight = calculateContainerHeight(durationMinutes),
    )
}

private const val MINUTES_THRESHOLD = 15
private const val EIGHT_HOURS_IN_MINUTES = 8 * 60
private const val HEIGHT_GAP = 12

private fun calculateContainerHeight(durationInMinutes: Int): Dp {
    val minHeight = 48
    val maxHeight = 180

    return if (durationInMinutes in 0..MINUTES_THRESHOLD) {
        minHeight.dp
    } else if (durationInMinutes > EIGHT_HOURS_IN_MINUTES) {
        maxHeight.dp
    } else {
        val increment =
            (maxHeight - minHeight) / (EIGHT_HOURS_IN_MINUTES - MINUTES_THRESHOLD).toFloat()
        val initialHeight = minHeight + (durationInMinutes - 15) * increment
        val roundedHeight = (initialHeight / HEIGHT_GAP).toInt() * HEIGHT_GAP

        roundedHeight.dp
    }
}