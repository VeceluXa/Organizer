package com.danilovfa.organizer.tasks.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.danilovfa.organizer.tasks.ui.model.TaskState
import com.danilovfa.organizer.tasks.utils.Constants.FOUR_HOURS_IN_MINUTES
import com.danilovfa.organizer.tasks.utils.Constants.HOUR_IN_MINUTES
import com.danilovfa.organizer.tasks.utils.TimeFormatter

@Composable
fun TaskCard(
    task: TaskState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val cardColor = when (task.durationInMinutes) {
        in 0 until HOUR_IN_MINUTES -> MaterialTheme.colorScheme.secondaryContainer
        in HOUR_IN_MINUTES until FOUR_HOURS_IN_MINUTES -> MaterialTheme.colorScheme.tertiaryContainer
        else -> MaterialTheme.colorScheme.errorContainer
    }

    val textColor = when (task.durationInMinutes) {
        in 0 until 60 -> MaterialTheme.colorScheme.onSecondaryContainer
        in 60 until 60 * 4 -> MaterialTheme.colorScheme.onTertiaryContainer
        else -> MaterialTheme.colorScheme.onErrorContainer
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .height(task.containerHeight)
            .background(color = cardColor, shape = MaterialTheme.shapes.medium)
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onClick)
            .padding(8.dp),
    ) {
        Text(
            text = task.title,
            style = MaterialTheme.typography.titleSmall,
            color = textColor,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = TimeFormatter.durationToString(task.durationInMinutes),
            style = MaterialTheme.typography.labelLarge,
            color = textColor,
            modifier = Modifier.alpha(0.8f)
        )
    }
}