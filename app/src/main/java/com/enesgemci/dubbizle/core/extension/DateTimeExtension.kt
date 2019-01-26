package com.enesgemci.dubbizle.core.extension

import com.enesgemci.dubbizle.core.util.DateUtils
import org.joda.time.DateTime

/**
 * Created by enesgemci on 04/10/2017.
 */
fun DateTime?.getFormattedDate(): String {
    this?.let {
        val day = this.dayOfMonth
        val month = this.monthOfYear
        val year = this.year

        return DateUtils.getFormattedDate(year, month, day)
    } ?: return ""
}

fun DateTime?.getFormattedDateTime(): String =
    this?.let { this.toDateTimeISO().toString().getFormattedDateTime() } ?: ""