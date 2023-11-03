package moadgara.base

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DateUtil {

    fun getDateRangeForWeek(week: Int?): Pair<String, String> {
        val calendar = Calendar.getInstance()

        val currentYear = calendar.get(Calendar.YEAR)
        val currentWeek = week ?: getCurrentWeek()

        calendar.firstDayOfWeek = Calendar.MONDAY // Set the first day of the week to Monday
        calendar.minimalDaysInFirstWeek = 4  // Set minimal days in the first week

        // Set the calendar to the first day of the specified year
        calendar.set(Calendar.YEAR, currentYear)
        calendar.set(Calendar.MONTH, Calendar.JANUARY)
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        // Move to the first day of the specified week
        calendar.add(Calendar.WEEK_OF_YEAR, currentWeek)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)

        // Format the start and end dates in "yyyy-MM-dd" format
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val startDate = dateFormatter.format(calendar.time)

        // Calculate the end date by adding 6 days to the start date
        calendar.add(Calendar.DAY_OF_MONTH, 6)
        val endDate = dateFormatter.format(calendar.time)

        return Pair(startDate, endDate)
    }

    fun getDateRangeForMonth(): Pair<String, String> {
        val calendar = Calendar.getInstance()

        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH) + 1

        // Set the calendar to the first day of the specified month and year
        calendar.set(Calendar.YEAR, currentYear)
        calendar.set(Calendar.MONTH, currentMonth - 1) // Month is 0-based, so subtract 1
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        // Format the start date in "yyyy-MM-dd" format
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val startDate = dateFormatter.format(calendar.time)

        // Calculate the end date by adding one month and subtracting one day
        calendar.add(Calendar.MONTH, 1)
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        val endDate = dateFormatter.format(calendar.time)

        return Pair(startDate, endDate)
    }

    fun getCurrentWeek(): Int {
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.minimalDaysInFirstWeek = 4
        return calendar.get(Calendar.WEEK_OF_YEAR)
    }

}