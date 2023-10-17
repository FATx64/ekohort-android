package com.example.ekohort_android.utils

import java.util.Calendar

class DateUtils {
    fun getCurrentDate(): String {
        val currentDateTime = Calendar.getInstance()
        val dayOfWeek = currentDateTime.get(Calendar.DAY_OF_WEEK)
        val day = currentDateTime.get(Calendar.DAY_OF_MONTH)
        val month = currentDateTime.get(Calendar.MONTH)
        val year = currentDateTime.get(Calendar.YEAR)

        val dayOfWeekString = when (dayOfWeek) {
            Calendar.SUNDAY -> "Sunday"
            Calendar.MONDAY -> "Monday"
            Calendar.TUESDAY -> "Tuesday"
            Calendar.WEDNESDAY -> "Wednesday"
            Calendar.THURSDAY -> "Thursday"
            Calendar.FRIDAY -> "Friday"
            Calendar.SATURDAY -> "Saturday"
            else -> ""
        }

        val monthString = when (month) {
            Calendar.JANUARY -> "January"
            Calendar.FEBRUARY -> "February"
            Calendar.MARCH -> "March"
            Calendar.APRIL -> "April"
            Calendar.MAY -> "May"
            Calendar.JUNE -> "June"
            Calendar.JULY -> "July"
            Calendar.AUGUST -> "August"
            Calendar.SEPTEMBER -> "September"
            Calendar.OCTOBER -> "October"
            Calendar.NOVEMBER -> "November"
            Calendar.DECEMBER -> "December"
            else -> ""
        }

        return "$dayOfWeekString, $day $monthString $year"
    }
}