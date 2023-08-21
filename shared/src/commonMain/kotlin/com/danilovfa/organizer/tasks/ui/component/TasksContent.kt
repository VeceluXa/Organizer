package com.danilovfa.organizer.tasks.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danilovfa.organizer.tasks.ui.store.TasksStore
import dev.icerock.moko.resources.compose.stringResource
import com.danilovfa.organizer.resources.MR

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TasksContent(
    state: TasksStore.State,
    onEvent: (TasksStore.Intent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(TasksStore.Intent.OnAddNewTask)
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = stringResource(MR.strings.add_new_task)
                )
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(10.dp)
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = stringResource(MR.strings.app_name),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }

            items(state.banners.size) { position ->
                ExpandableBannerCard(
                    state.banners[position],
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                )
            }

            stickyHeader {
                Text(
                    text = stringResource(MR.strings.tasks_for_the_day),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.background)
                        .padding(12.dp)
                )
            }

            items(
                items = state.tasks,
                key = { task ->
                    task.id
                }
            ) { task ->
                TaskCard(task) {
                    onEvent(TasksStore.Intent.OnEditTask(task))
                }
            }
        }
    }
}