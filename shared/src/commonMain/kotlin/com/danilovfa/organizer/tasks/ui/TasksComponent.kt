package com.danilovfa.organizer.tasks.ui

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.danilovfa.organizer.tasks.ui.store.TasksStore
import com.danilovfa.organizer.tasks.ui.store.TasksStoreFactory
import kotlinx.coroutines.flow.StateFlow

class TasksComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory
): ComponentContext by componentContext {
    private val tasksStore = instanceKeeper.getStore {
        TasksStoreFactory(
            storeFactory = storeFactory
        ).create()
    }

    val state: StateFlow<TasksStore.State> = tasksStore.stateFlow

    fun onEvent(event: TasksStore.Intent) {
        tasksStore.accept(event)
    }
}