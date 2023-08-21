package com.danilovfa.organizer.tasks.ui.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.danilovfa.organizer.tasks.domain.model.Task
import com.danilovfa.organizer.tasks.domain.repository.BannerRepository
import com.danilovfa.organizer.tasks.domain.repository.TasksRepository
import com.danilovfa.organizer.tasks.ui.mapper.toState
import com.danilovfa.organizer.tasks.ui.model.BannerState
import com.danilovfa.organizer.tasks.ui.model.TaskState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
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
        data class BannersLoaded(val banners: List<BannerState>) : Msg()
        data class TasksLoaded(val tasks: List<TaskState>) : Msg()
        object OpenAddTaskSheet : Msg()
        data class OpenEditTaskSheet(val task: TaskState) : Msg()
        object DismissAddEditTask : Msg()
        data class ShowError(val message: String) : Msg()
        object DismissError : Msg()
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<TasksStore.Intent, Action, TasksStore.State, Msg, Nothing>(
            Dispatchers.Main
        ) {
        override fun executeIntent(intent: TasksStore.Intent, getState: () -> TasksStore.State) {
            when (intent) {
                is TasksStore.Intent.EditTask -> updateTask(intent.task)
                is TasksStore.Intent.DeleteTask -> deleteTask(intent.id)
                is TasksStore.Intent.CreateTask -> createTask(
                    intent.title,
                    intent.durationInMinutes
                )

                TasksStore.Intent.OnAddNewTask -> dispatch(Msg.OpenAddTaskSheet)
                TasksStore.Intent.DismissTaskBottomSheet -> dispatch(Msg.DismissAddEditTask)
                is TasksStore.Intent.OnEditTask -> dispatch(Msg.OpenEditTaskSheet(intent.task))
                is TasksStore.Intent.ShowError -> dispatch(Msg.ShowError(intent.message))
                TasksStore.Intent.DismissError -> dispatch(Msg.DismissError)
            }
        }

        override fun executeAction(action: Action, getState: () -> TasksStore.State) {
            when (action) {
                Action.LoadBanners -> loadBanners()
                Action.LoadTasks -> loadTasks()
            }
        }

        private fun createTask(title: String, durationInMinutes: Int) {
            scope.launch {
                tasksRepository.insertTask(title, durationInMinutes)
            }
        }

        private fun deleteTask(id: Long) {
            scope.launch {
                tasksRepository.deleteTask(id)
            }
        }

        private fun loadBanners() {
            scope.launch {
                val banners = bannerRepository.getBanners().map { banner ->
                    banner.toState()
                }
                dispatch(Msg.BannersLoaded(banners))
            }
        }

        private fun loadTasks() {
            scope.launch {
                tasksRepository.getTodaysTasks().map { tasksList ->
                    tasksList.map { task ->
                        task.toState()
                    }
                }.collect { tasks ->
                    dispatch(Msg.TasksLoaded(tasks))
                }
            }
        }

        private fun updateTask(task: Task) {
            scope.launch {
                tasksRepository.updateTask(task)
            }
        }
    }

    private object ReducerImpl : Reducer<TasksStore.State, Msg> {
        override fun TasksStore.State.reduce(msg: Msg): TasksStore.State =
            when (msg) {
                is Msg.BannersLoaded -> copy(banners = msg.banners)
                is Msg.TasksLoaded -> copy(tasks = msg.tasks)
                Msg.OpenAddTaskSheet -> copy(
                    isTasksBottomSheetOpen = true
                )

                is Msg.OpenEditTaskSheet -> copy(
                    isTasksBottomSheetOpen = true,
                    selectedTask = msg.task
                )

                Msg.DismissAddEditTask -> copy(
                    isTasksBottomSheetOpen = false,
                    selectedTask = null
                )

                is Msg.ShowError -> copy(
                    error = msg.message
                )

                Msg.DismissError -> copy(
                    error = null
                )
            }
    }

    companion object {
        const val STORE_NAME = "TasksStore"
    }
}