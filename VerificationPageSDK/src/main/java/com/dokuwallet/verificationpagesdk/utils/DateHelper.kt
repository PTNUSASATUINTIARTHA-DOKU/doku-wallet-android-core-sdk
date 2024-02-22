package com.dokuwallet.verificationpagesdk.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DateHelper {
    const val birthDateFormat = "dd MMMM yyyy"
    fun calendarToString(calendar: Calendar, format: String): String {
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    fun Calendar.withTimeAtStartOfDay() {
        clear(Calendar.HOUR_OF_DAY)
        clear(Calendar.MINUTE)
        clear(Calendar.SECOND)
        clear(Calendar.MILLISECOND)
    }
}