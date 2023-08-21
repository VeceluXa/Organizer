package com.danilovfa.organizer.tasks.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.stringResource
import com.danilovfa.organizer.resources.MR
import com.danilovfa.organizer.tasks.domain.model.Task
import com.danilovfa.organizer.tasks.ui.model.TaskState
import com.danilovfa.organizer.tasks.ui.store.TasksStore
import com.danilovfa.organizer.tasks.utils.Constants.HOUR_IN_MINUTES
import com.danilovfa.organizer.tasks.utils.TimeFormatter
import com.danilovfa.organizer.ui.theme.icons.DeleteIcon

@Composable
fun TaskBottomSheetContent(
    isVisible: Boolean,
    taskState: TaskState? = null,
    onEvent: (TasksStore.Intent) -> Unit,
    onDismiss: () -> Unit
) {
    var taskTitle by remember(taskState, isVisible) { mutableStateOf(taskState?.title ?: "") }
    var taskDuration by remember(taskState, isVisible) {
        mutableStateOf(taskState?.durationInMinutes ?: 0)
    }

    var buttonPickerText by remember(taskState, isVisible) {
        mutableStateOf(TimeFormatter.durationToString(taskState?.durationInMinutes))
    }

    val error = if (taskTitle == "")
        stringResource(MR.strings.error_empty_title)
    else if (taskDuration == 0)
        stringResource(MR.strings.error_empty_duration)
    else
        null

    var isTimePickerOpen by remember { mutableStateOf(false) }
    if (isTimePickerOpen) {
        TimePickerDialog(
            title = stringResource(MR.strings.duration),
            initialHours = taskDuration / HOUR_IN_MINUTES,
            initialMinutes = taskDuration % HOUR_IN_MINUTES,
            onDismiss = { isTimePickerOpen = false },
            onTimeSelected = { hours, minutes ->
                taskDuration = hours * HOUR_IN_MINUTES + minutes
                buttonPickerText =
                    if (taskDuration != 0) TimeFormatter.durationToString(taskDuration) else ""
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(422.dp)
            .background(Color.Transparent)
            .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextFieldWithClearButton(
            value = taskTitle,
            label = stringResource(MR.strings.title),
            requirement = stringResource(MR.strings.title_requirements),
            maxTextLength = 40
        ) {
            taskTitle = it
        }

        Spacer(Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { isTimePickerOpen = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    top = 10.dp,
                    bottom = 10.dp,
                    end = 24.dp
                )
            ) {
                Icon(
                    imageVector = if (buttonPickerText == "") Icons.Rounded.Add else Icons.Rounded.Edit,
                    contentDescription = null
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = if (buttonPickerText == "")
                        stringResource(MR.strings.select_duration)
                    else
                        buttonPickerText,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            taskState?.id?.let { taskId ->
                IconButton(
                    onClick = {
                        onEvent(TasksStore.Intent.DeleteTask(taskId))
                        onDismiss()
                    }
                ) {
                    Icon(
                        imageVector = DeleteIcon(),
                        contentDescription = stringResource(MR.strings.delete),
                        tint = MaterialTheme.colorScheme.secondaryContainer,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                if (error != null) {
                    onEvent(TasksStore.Intent.ShowError(error))
                } else if (taskState != null) {
                    val newTask = Task(
                        id = taskState.id,
                        title = taskTitle,
                        createdAtEpoch = taskState.createdAtEpoch,
                        durationMinutes = taskDuration
                    )
                    onEvent(TasksStore.Intent.EditTask(newTask))
                    onDismiss()
                } else {
                    onEvent(TasksStore.Intent.CreateTask(taskTitle, taskDuration))
                    onDismiss()
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            contentPadding = PaddingValues(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Save,
                contentDescription = stringResource(MR.strings.save)
            )
        }
    }
}