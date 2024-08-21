package com.baysoftware.bayfit.util




fun Double.getTimeStringFromDouble(): String {
    val totalSeconds = this.toInt()
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}