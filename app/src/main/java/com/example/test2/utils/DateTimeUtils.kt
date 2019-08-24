package com.example.test2.utils

import java.text.SimpleDateFormat
import java.util.*

const val DEFAULT_FORMAT = "yyyy/MM/dd HH:mm:ss"
const val YYYY_MM_DD_FORMAT = "yyyy/MM/dd"
const val YYYY_MM_FORMAT = "yyyy/MM"

fun convertStringToDate(date: String): Date? {
    return SimpleDateFormat(DEFAULT_FORMAT).parse(date)
}

fun convertStringToTimeStamp(date: String): Long {
    val tmpDate = convertStringToDate(date)
    return if (tmpDate == null) 0 else tmpDate.time
}

fun convertTimeStampToString(timeStamp: Long, format: String? = DEFAULT_FORMAT): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timeStamp
    return android.text.format.DateFormat.format(format, calendar).toString()
}

fun getWeekOfMonth(timeStamp: Long): Int {
    val ca1 = Calendar.getInstance()
    ca1.timeInMillis = timeStamp
    return ca1.get(Calendar.WEEK_OF_MONTH)
}

fun getDateOfMonth(timeStamp: Long): Int {
    val ca1 = Calendar.getInstance()
    ca1.timeInMillis = timeStamp
    return ca1.get(Calendar.DAY_OF_MONTH)
}

fun getHourOfDay(timeStamp: Long): Int {
    val ca1 = Calendar.getInstance()
    ca1.timeInMillis = timeStamp
    return ca1.get(Calendar.HOUR_OF_DAY)
}

fun getTimeNoData(day: Int?, hour: Int?): Long {
    return if (day != null) {
        if (hour == null) {
            convertStringToTimeStamp(String.format("2016/01/%d 00:00:00", day))
        } else {
            convertStringToTimeStamp(
                    String.format(
                            "2016/01/%d %s:00:00",
                            day,
                            if (hour < 10) "0$hour" else hour.toString()
                    )
            )
        }
    } else {
        convertStringToTimeStamp("2016/01/01 00:00:00")
    }
}
