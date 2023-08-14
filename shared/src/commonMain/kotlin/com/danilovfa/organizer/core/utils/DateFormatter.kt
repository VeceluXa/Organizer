package com.danilovfa.organizer.core.utils

import kotlinx.datetime.LocalDate

fun LocalDate.toFormattedString(): String {
    return "${dayOfMonth.zeroPrefixed(2)}.${monthNumber.zeroPrefixed(2)}.${year}"
}

private fun Int.zeroPrefixed(zerosCount: Int) = this.toString().padStart(zerosCount, '0')