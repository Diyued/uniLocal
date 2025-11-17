package com.example.unilocal.model

import java.time.DayOfWeek
import java.time.LocalTime

data class Schedule(
    val day: DayOfWeek = DayOfWeek.MONDAY,     // DayOfWeek -> String
    val open: String = "09:00",     // LocalTime -> String
    val close: String = "18:00"     // LocalTime -> String
) {
    fun getOpenTime(): LocalTime = LocalTime.parse(open)
    fun getCloseTime(): LocalTime = LocalTime.parse(close)

    fun toDisplayString(): String {
        return "${day}: $open - $close"
    }
}
