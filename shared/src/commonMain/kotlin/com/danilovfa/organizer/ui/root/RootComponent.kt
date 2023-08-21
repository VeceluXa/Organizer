package com.danilovfa.organizer.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.danilovfa.organizer.tasks.ui.TasksComponent

class RootComponent(
    componentContext: ComponentContext,
    private val tasks: (ComponentContext) -> TasksComponent
) : ComponentContext by componentContext {

    constructor(
        componentContext: ComponentContext,
        storeFactory: StoreFactory
    ) : this(
        componentContext = componentContext,
        tasks = { childContext ->
            TasksComponent(
                componentContext = childContext,
                storeFactory = storeFactory
            )
        }
    )

    private val navigation = StackNavigation<Configuration>()

    private val stack = childStack(
        source = navigation,
        initialConfiguration = Configuration.Tasks,
        handleBackButton = false,
        childFactory = ::createChild
    )

    val childStack: Value<ChildStack<*, Child>> = stack

    private fun createChild(
        configuration: Configuration,
        componentContext: ComponentContext
    ): Child =
        when (configuration) {
            Configuration.Tasks -> Child.Tasks(tasks(componentContext))
        }


    private sealed class Configuration : Parcelable {
        @Parcelize
        object Tasks : Configuration()
    }

    sealed class Child {
        data class Tasks(val component: TasksComponent) : Child()
    }
}