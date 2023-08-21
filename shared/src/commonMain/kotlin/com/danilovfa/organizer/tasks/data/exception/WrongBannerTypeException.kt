package com.danilovfa.organizer.tasks.data.exception

class WrongBannerTypeException: Exception() {
    override val message: String
        get() = MESSAGE

    companion object {
        const val MESSAGE = "Wrong banner type exception."
    }
}