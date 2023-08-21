package com.danilovfa.organizer.tasks.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import com.danilovfa.organizer.tasks.ui.component.MessageDialog
import com.danilovfa.organizer.tasks.ui.component.TaskBottomSheetContent
import com.danilovfa.organizer.tasks.ui.component.TasksContent
import com.danilovfa.organizer.tasks.ui.store.TasksStore
import dev.icerock.moko.resources.compose.stringResource
import com.danilovfa.organizer.resources.MR
import kotlinx.coroutines.launch
import androidx.compose.material3.MaterialTheme as Material3Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    component: TasksComponent
) {
    val state by component.state.collectAsState()
    val scope = rememberCoroutineScope()

    state.error?.let { errorMessage ->
        MessageDialog(
            title = stringResource(MR.strings.error),
            message = errorMessage
        ) {
            component.onEvent(TasksStore.Intent.DismissError)
        }
    }

    var recompositionCount by remember { mutableStateOf(0) }
    SideEffect {
        recompositionCount += 1
        Logger.withTag("TasksScreen").i { "Recompositions: $recompositionCount" }
    }

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(state.isTasksBottomSheetOpen) {
        if (state.isTasksBottomSheetOpen && !bottomSheetState.isVisible) {
            bottomSheetState.show()
        } else if (!state.isTasksBottomSheetOpen && bottomSheetState.isVisible) {
            bottomSheetState.hide()
        }
    }

    if (state.isTasksBottomSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = { component.onEvent(TasksStore.Intent.DismissTaskBottomSheet) },
            sheetState = bottomSheetState,
            dragHandle = {
                Box(
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 32.dp)
                        .height(6.dp)
                        .width(48.dp)
                        .background(
                            color = Material3Theme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(32.dp)
                        )

                )
            },
            containerColor = Material3Theme.colorScheme.surface
        ) {
            TaskBottomSheetContent(
                isVisible = bottomSheetState.isVisible,
                taskState = state.selectedTask,
                onEvent = { event ->
                    component.onEvent(event)
                },
                onDismiss = {
                    scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                        component.onEvent(TasksStore.Intent.DismissTaskBottomSheet)
                    }
                }
            )
        }
    }

    TasksContent(
        state = state,
        onEvent = component::onEvent
    )
}