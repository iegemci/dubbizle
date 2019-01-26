package com.enesgemci.dubbizle.core.extension

import android.graphics.Typeface
import android.os.Build
import android.text.*
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import org.joda.time.DateTime
import org.joda.time.DateTimeZone


/**
 * Created by enesgemci on 02/10/2017.
 */

val String?.htmlValue: Spanned?
    get() {
        return this?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(this)
            }
        }
    }

fun String?.getIsoFormattedDateTime(): DateTime {
    try {
        this?.let {
            if (!TextUtils.isEmpty(this)) {
                val sp = this.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

                if (sp.size == 3) {
                    val year = sp[2].trim { it <= ' ' }.replace(" ", "")
                    var month = sp[0].trim { it <= ' ' }.replace(" ", "")
                    var day = sp[1].trim { it <= ' ' }.replace(" ", "")

                    month = if (month.length == 1) "0$month" else month
                    day = if (day.length == 1) "0$day" else day

                    return "$year-$month-$day".parseIso8601Date()
                }

                return DateTime(DateTimeZone.UTC)
            }
        }

        return DateTime(DateTimeZone.UTC)
    } catch (e: Exception) {
        return DateTime(DateTimeZone.UTC)
    }
}

fun String?.parseIso8601Date(): DateTime =
    this?.let { DateTime(this, DateTimeZone.UTC) } ?: DateTime(DateTimeZone.UTC)

fun String?.getFormattedDate(): String {
    this?.let {
        return this.parseIso8601Date().getFormattedDate()
    } ?: return ""
}

fun String?.getFormattedDateTime(): String {
    this?.let {
        val dateTime = this.parseIso8601Date()
        val date = dateTime.getFormattedDate()

        return date + " "
            .plus(if (dateTime.hourOfDay < 10) "0" + dateTime.hourOfDay else dateTime.hourOfDay)
            .plus(":")
            .plus(if (dateTime.minuteOfHour < 10) "0" + dateTime.minuteOfHour else dateTime.minuteOfHour)
    } ?: return ""
}

fun String.toDate(): DateTime? {
    if (!TextUtils.isEmpty(this)) {
        val spDate = this.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        if (spDate.size == 3) {
            return DateTime(Integer.parseInt(spDate[2]), Integer.parseInt(spDate[0]), Integer.parseInt(spDate[1]), 0, 0)
        }

        return null
    }

    return null
}

fun String.underline(): SpannableString {
    val content = SpannableString(this)
    content.setSpan(UnderlineSpan(), 0, content.length, 0)

    return content
}

fun CharSequence.underline(): SpannableString {
    val content = SpannableString(this)
    content.setSpan(UnderlineSpan(), 0, content.length, 0)

    return content
}

fun String?.isNotNullOrEmpty(): Boolean = !this.isNullOrEmpty()

fun String.capitalize(): String {
    val words = this.trim().split(" ")
    var capitalizedText = ""

    words
        .filter { it.isNotNullOrEmpty() }
        .forEach {
            capitalizedText += if (it.isNotEmpty() && it[0].isLowerCase()) it.substring(
                0,
                1
            ).toUpperCase() + it.substring(1) else this
            capitalizedText += " "
        }

    return capitalizedText.substring(0, capitalizedText.length - 1)
}

fun CharSequence.partialSpanWithColor(spanColor: Int, vararg stringToSpan: String): SpannableStringBuilder {
    return this.toString().partialSpanWithColor(spanColor, *stringToSpan)
}

fun String.partialSpanWithColor(spanColor: Int, vararg stringToSpan: String): SpannableStringBuilder {
    val spannableStringBuilder = SpannableStringBuilder(this)

    stringToSpan.forEach {
        if (!this.contains(it)) {
            return spannableStringBuilder
        }

        val startIndex = this.indexOf(it)
        val endIndex = startIndex + it.length

        spannableStringBuilder.setSpan(
            ForegroundColorSpan(spanColor),
            startIndex,
            endIndex,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
    }

    return spannableStringBuilder
}

fun CharSequence.partialBoldSpan(vararg stringToSpan: String): SpannableStringBuilder {
    return this.toString().partialBoldSpan(*stringToSpan)
}

fun SpannableStringBuilder.partialBoldSpan(vararg stringToSpan: String): SpannableStringBuilder {
    return this.toString().partialBoldSpan(*stringToSpan)
}

fun String.partialBoldSpan(vararg stringToSpan: String): SpannableStringBuilder {
    val spannableStringBuilder = SpannableStringBuilder(this)

    stringToSpan.forEach {
        if (!this.contains(it)) {
            return spannableStringBuilder
        }

        val startIndex = this.indexOf(it)
        val endIndex = startIndex + it.length

        spannableStringBuilder.setSpan(
            StyleSpan(Typeface.BOLD),
            startIndex,
            endIndex,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
    }

    return spannableStringBuilder
}