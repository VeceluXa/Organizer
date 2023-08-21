package com.danilovfa.organizer.core.utils

fun Int.zeroPrefixed(zerosCount: Int) = this.toString().padStart(zerosCount, '0')