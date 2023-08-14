package com.danilovfa.organizer.tasks.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.danilovfa.organizer.tasks.ui.component.TasksContent

@Composable
fun TasksScreen(
    component: TasksComponent
) {
    val state by component.state.collectAsState()

    TasksContent(
        state = state,
        onEvent = component::onEvent
    )
}