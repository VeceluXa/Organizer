package com.danilovfa.organizer.tasks.ui.store

import com.arkivanov.mvikotlin.core.store.Store
import com.danilovfa.organizer.tasks.domain.model.Task
import com.danilovfa.organizer.tasks.ui.model.BannerState
import com.danilovfa.organizer.tasks.ui.model.TaskState

interface TasksStore: Store<TasksStore.Intent, TasksStore.State, Nothing> {
    sealed class Intent {
        object OnAddNewTask : Intent()
        data class OnEditTask(val task: TaskState) : Intent()
        data class CreateTask(val title: String, val durationInMinutes: Int) : Intent()
        data class EditTask(val task: Task) : Intent()
        data class DeleteTask(val id: Long) : Intent()
        data class ShowError(val message: String) : Intent()
        object DismissError : Intent()
        object DismissTaskBottomSheet : Intent()
    }

    data class State(
        val banners: List<BannerState> = emptyList(),
        val tasks: List<TaskState> = emptyList(),
        val selectedTask: TaskState? = null,
        val isTasksBottomSheetOpen: Boolean = false,
        val error: String? = null
    )
}