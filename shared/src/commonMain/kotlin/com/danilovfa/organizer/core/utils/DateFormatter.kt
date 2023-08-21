package com.danilovfa.organizer.core.utils

import kotlinx.datetime.LocalDate

fun LocalDate.toFormattedString(): String {
    return "${dayOfMonth.zeroPrefixed(2)}.${monthNumber.zeroPrefixed(2)}.${year}"
}