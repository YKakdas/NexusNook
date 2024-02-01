package com.moadgara.common_model

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DateUtil {

    fun getDateRangeForWeek(weekNumber: Int): List<String> {
        val calendar = Calendar.getInstance()

        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.set(Calendar.WEEK_OF_YEAR, weekNumber)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)

        val startDate = calendar.time

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        val endDate = calendar.time

        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val startString = dateFormatter.format(startDate)
        val endString = dateFormatter.format(endDate)

        return listOf(startString, endString)
    }

    fun getDateRangeForMonth(): List<String> {
        val calendar = Calendar.getInstance()

        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val startDate = calendar.time

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        val endDate = calendar.time

        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val startString = dateFormatter.format(startDate)
        val endString = dateFormatter.format(endDate)

        return listOf(startString, endString)
    }

    // If it is first 6 month of the new year, still show last years' games as best of the year, otherwise use the current year
    fun getDateRangeForYear(): List<String> {
        val calendar = Calendar.getInstance()

        val month = calendar.get(Calendar.MONTH)
        var year = calendar.get(Calendar.YEAR)

        if (month < 6) {
            year -= 1
        }

        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.DAY_OF_YEAR, 1)
        val startDate = calendar.time

        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR))
        val endDate = calendar.time

        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val startString = dateFormatter.format(startDate)
        val endString = dateFormatter.format(endDate)

        return listOf(startString, endString)
    }

    fun getCurrentWeek(): Int {
        val calendar = Calendar.getInstance()
        val weekNumber = calendar.get(Calendar.WEEK_OF_YEAR)
        return weekNumber
    }

}