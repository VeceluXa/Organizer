package com.danilovfa.organizer.tasks.ui.store

import com.arkivanov.mvikotlin.core.store.Store
import com.danilovfa.organizer.tasks.ui.model.BannerState
import com.danilovfa.organizer.tasks.ui.model.TaskState

interface TasksStore: Store<TasksStore.Intent, TasksStore.State, Nothing> {
    sealed class Intent {
        object OnAddNewTask : Intent()
        object DismissTask: Intent()
        data class OnTaskTitleChanged(val value: String) : Intent()
        data class OnHoursChanged(val value: String) : Intent()
        data class OnMinutesChanged(val value: String) : Intent()
        data class SelectTask(val task: TaskState) : Intent()
        data class EditTask(val task: TaskState) : Intent()
        object DeleteTask : Intent()
    }

    data class State(
        val banners: List<BannerState> = emptyList(),
        val selectedBannerPosition: Int? = null,
        val tasks: List<TaskState> = emptyList(),
        val selectedTask: TaskState? = null,
        val isAddTaskSheetOpen: Boolean = false,
        val isEditTaskSheetOpen: Boolean = false,
        val error: String? = null
    )
}