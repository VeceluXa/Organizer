package com.danilovfa.organizer.core.utils

import java.util.UUID

actual class UUID {
    actual companion object {
        actual fun generate() = UUID.randomUUID().toString()
    }
}