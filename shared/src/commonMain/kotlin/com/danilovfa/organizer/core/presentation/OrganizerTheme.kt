package com.danilovfa.organizer.core.presentation

import androidx.compose.runtime.Composable

@Composable
expect fun OrganizerTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
)