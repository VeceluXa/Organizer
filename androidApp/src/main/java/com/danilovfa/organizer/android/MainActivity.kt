package com.danilovfa.organizer.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.danilovfa.organizer.core.presentation.OrganizerTheme
import com.danilovfa.organizer.ui.root.RootComponent
import com.danilovfa.organizer.ui.root.RootContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootComponent = RootComponent(
            componentContext = defaultComponentContext(),
            storeFactory = DefaultStoreFactory()
        )

        setContent {
            OrganizerTheme(
                darkTheme = isSystemInDarkTheme()
            ) {
                RootContent(rootComponent)
            }
        }
    }
}