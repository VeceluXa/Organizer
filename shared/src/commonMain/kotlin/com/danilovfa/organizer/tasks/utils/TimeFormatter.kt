package com.danilovfa.organizer.tasks.utils

import com.danilovfa.organizer.core.utils.zeroPrefixed

object TimeFormatter {
    fun durationToString(durationInMinutes: Int?): String {
        if (durationInMinutes == null) return ""

        val hours = durationInMinutes / Constants.HOUR_IN_MINUTES
        val minutes = durationInMinutes % Constants.HOUR_IN_MINUTES
        return "${hours.zeroPrefixed(2)}:${minutes.zeroPrefixed(2)}"
    }
}