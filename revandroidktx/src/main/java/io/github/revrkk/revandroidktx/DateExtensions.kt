package io.github.revrkk.revandroidktx

import java.text.SimpleDateFormat
import java.util.*

const val YYYY_MM_DD__HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS"
const val DD_MM_YYYY = "dd-MM-yyyy"

fun Date.toSimpleDate(): String = SimpleDateFormat(DD_MM_YYYY, Locale.US).format(this)

fun Date.toSimpleDateWithTimeAndMillis(): String = SimpleDateFormat(YYYY_MM_DD__HH_MM_SS_SSS, Locale.US).format(this)


fun Date.isOnSameDayAs(vararg dates: Date): Boolean {
    val fmt = SimpleDateFormat("yyyyMMdd", Locale.US)
    val date1 = fmt.format(this)
    for (date in dates)
        if (fmt.format(date) != date1)
            return false

    return true
}