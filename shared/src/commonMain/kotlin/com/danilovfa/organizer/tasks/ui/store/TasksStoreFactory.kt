package com.danilovfa.organizer.tasks.ui.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.danilovfa.organizer.tasks.domain.repository.BannerRepository
import com.danilovfa.organizer.tasks.domain.repository.TasksRepository
import kotlinx.coroutines.Dispatchers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TasksStoreFactory(
    private val storeFactory: StoreFactory
) : KoinComponent {

    private val bannerRepository by inject<BannerRepository>()
    private val tasksRepository by inject<TasksRepository>()

    fun create(): TasksStore =
        object : TasksStore,
            Store<TasksStore.Intent, TasksStore.State, Nothing> by storeFactory.create(
                name = STORE_NAME,
                initialState = TasksStore.State(),
                bootstrapper = SimpleBootstrapper(Action.LoadBanners, Action.LoadTasks),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    sealed class Action {
        object LoadBanners : Action()
        object LoadTasks : Action()
    }

    private sealed class Msg {

    }

    private inner class ExecutorImpl :
        CoroutineExecutor<TasksStore.Intent, Action, TasksStore.State, Msg, Nothing>(
            Dispatchers.Main
        ) {
        override fun executeIntent(intent: TasksStore.Intent, getState: () -> TasksStore.State) {
            when (intent) {
                else -> {}
            }
        }

        override fun executeAction(action: Action, getState: () -> TasksStore.State) {
            when (action) {
                else -> {}
            }
        }
    }

    private object ReducerImpl : Reducer<TasksStore.State, Msg> {
        override fun TasksStore.State.reduce(msg: Msg): TasksStore.State =
            when (msg) {
                else -> { TODO() }
            }
    }

    companion object {
        const val STORE_NAME = "TasksStore"
    }
}