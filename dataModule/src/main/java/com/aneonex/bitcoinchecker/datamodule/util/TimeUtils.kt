package com.aneonex.bitcoinchecker.datamodule.util

object TimeUtils {
//    const val NANOS_IN_MILLIS: Long = 1000
    const val MILLIS_IN_SECOND: Long = 1000
    const val MILLIS_IN_MINUTE = 60 * MILLIS_IN_SECOND
    const val MILLIS_IN_HOUR = 60 * MILLIS_IN_MINUTE
    const val MILLIS_IN_DAY = 24 * MILLIS_IN_HOUR
    private const val MILLIS_IN_YEAR = 365 * MILLIS_IN_DAY
    fun parseTimeToMillis(time: Long): Long {
        if (time < MILLIS_IN_YEAR) return time * MILLIS_IN_SECOND else if (time > 5000 * MILLIS_IN_YEAR) return time / 1000
        return time
    }
}