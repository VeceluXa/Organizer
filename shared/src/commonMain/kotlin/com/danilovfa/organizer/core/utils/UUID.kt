package com.danilovfa.organizer.core.utils

expect class UUID {
    companion object {
        fun generate(): String
    }
}