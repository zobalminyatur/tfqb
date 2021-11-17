package com.tresfellas.queenbee.utils

import android.content.Context
import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import com.tresfellas.queenbee.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

object TimeUtil {
    private const val DATE_FORMAT_PATTEN = "yyyy-MM-dd'T'HH:mm:ss.SSSS"

    private val local =
        ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0)
    private val dateFormat = SimpleDateFormat(DATE_FORMAT_PATTEN, local)
    private val timeFormat = SimpleDateFormat("a h:mm",local)
    private val dayFormat = SimpleDateFormat("yyyy.MM.dd",local)

    init {
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    }

    fun getSimpleDateFormat(): SimpleDateFormat {
        return dateFormat
    }

    fun getTimeFormat(time: String): String{
        val date = dateFormat.parse(time)
        return timeFormat.format(date)
    }

    fun getDayFormat(time: String): String{
        val date = dateFormat.parse(time)
        return dayFormat.format(date)
    }

    fun getCurrentDateToString(): String {
        return dateFormat.format(Date())
    }

    fun getAnHourLaterTimeToString(time: String): String {
        val date = dateFormat.parse(time)
        val calendar = Calendar.getInstance()

        calendar.time = date
        calendar.add(Calendar.HOUR, 1)

        return dateFormat.format(calendar.time)
    }

    fun timeAgo(context: Context, createdTime: String): String {
        val past = dateFormat.parse(createdTime)
        val now = Date()

        val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time)
        val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
        val hours: Long = TimeUnit.MILLISECONDS.toHours(now.time - past.time)
        val days: Long = TimeUnit.MILLISECONDS.toDays(now.time - past.time)

        return when {
            seconds < 60 -> {
                seconds.toString() + context.getString(R.string.time_sec_ago)
            }
            minutes < 60 -> {
                minutes.toString() + context.getString(R.string.time_min_ago)
            }
            hours < 24 -> {
                hours.toString() + context.getString(R.string.time_hour_ago)
            }
            else -> {
                days.toString() + context.getString(R.string.time_day_ago)
            }
        }
    }

    fun convertVideoCreatedTimeToMilliSec(createdTime: String): Long {
        val past = dateFormat.parse(createdTime)
        return TimeUnit.MILLISECONDS.toSeconds(past.time)
    }

    fun convertDurationtoHMS(duration: String): String {
        val pti = duration.indexOf("T")
        val hi = duration.indexOf("H")
        val mi = duration.indexOf("M")
        val si = duration.indexOf("S")

        return when {
            mi == -1 -> {
                val ss = duration.substring(pti + 1, si).toDouble()
                val sss = ss.roundToInt().toString()
                sss + "sec"
            }
            hi == -1 -> {
                val mm = duration.substring(pti + 1, mi)
                mm + "min"
            }
            else -> {
                val hh = duration.substring(pti + 1, hi)
                val mm = duration.substring(hi + 1, mi)
                hh + "hrs" + " " + mm + "min"
            }
        }
    }

    fun millisectoHHMMSS(millisUntilFinished: Long): String {
        return String.format(
            "%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % TimeUnit.HOURS.toMinutes(
                1
            ),
            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % TimeUnit.MINUTES.toSeconds(
                1
            )
        )
    }

    fun videoTimeFormat(createdTime: String): String {
        val mediumDateFormat: DateFormat = DateFormat.getDateTimeInstance(
            DateFormat.LONG,
            DateFormat.SHORT
        )
        val createdTimeToDate = dateFormat.parse(createdTime)
        return mediumDateFormat.format(createdTimeToDate)
    }
}