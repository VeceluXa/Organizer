package com.danilovfa.organizer.core.data

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.danilovfa.organizer.tasksDatabase.TasksDatabase

actual class DatabaseDriverFactory(
    private val context: Context
) {
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(
            TasksDatabase.Schema,
            context,
            DATABASE_NAME
        )
    }

    companion object {
        const val DATABASE_NAME = "tasks.db"
    }
}