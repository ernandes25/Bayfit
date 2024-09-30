package com.baysoftware.bayfit.util

import java.util.Locale

fun Long.toParsedDuration(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val seconds = this % 60
    return String.format(Locale.US,"%02d:%02d:%02d", hours, minutes, seconds)
}