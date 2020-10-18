package com.aneonex.bitcoinchecker.datamodule.util

import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {
//    const val NANOS_IN_MILLIS: Long = 1000
    const val MILLIS_IN_SECOND: Long = 1000
    const val MILLIS_IN_MINUTE = 60 * MILLIS_IN_SECOND
    const val MILLIS_IN_HOUR = 60 * MILLIS_IN_MINUTE
    const val MILLIS_IN_DAY = 24 * MILLIS_IN_HOUR
    const val MILLIS_IN_YEAR = 365 * MILLIS_IN_DAY

    private val ISO_DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ROOT)

    init {
        ISO_DATE_FORMAT.timeZone = TimeZone.getTimeZone("GMT")
    }

    fun parseTimeToMillis(time: Long): Long {
        if (time < MILLIS_IN_YEAR) return time * MILLIS_IN_SECOND else if (time > 5000 * MILLIS_IN_YEAR) return time / 1000
        return time
    }

    // Parsing string and converting to timestamp.
    //  Returns 0 if parsing failed
    fun convertISODateToTimestamp(isoDateString: String): Long {
        // This is an ISO timestamp representing UTC time
        return ISO_DATE_FORMAT.parse(isoDateString)?.time ?: 0
    }
}