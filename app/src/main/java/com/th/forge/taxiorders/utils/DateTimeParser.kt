package com.th.forge.taxiorders.utils

import java.text.SimpleDateFormat

object DateTimeParser {
    fun getReadableString(pattern: String, timeInMillis: Long): String {
        return SimpleDateFormat(pattern).format(timeInMillis)
    }

}
