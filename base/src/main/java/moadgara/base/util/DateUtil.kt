package moadgara.base.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DateUtil {

    fun getDateRangeForWeek(week: Int?): List<String> {
        val calendar = Calendar.getInstance()

        val currentYear = calendar.get(Calendar.YEAR)
        val currentWeek = week ?: getCurrentWeek()

        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.minimalDaysInFirstWeek = 4

        calendar.set(Calendar.YEAR, currentYear)
        calendar.set(Calendar.MONTH, Calendar.JANUARY)
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        calendar.add(Calendar.WEEK_OF_YEAR, currentWeek)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)

        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val startDate = dateFormatter.format(calendar.time)

        calendar.add(Calendar.DAY_OF_MONTH, 6)
        val endDate = dateFormatter.format(calendar.time)

        return listOf(startDate, endDate)
    }

    fun getDateRangeForMonth(): List<String> {
        val calendar = Calendar.getInstance()

        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)

        calendar.set(Calendar.YEAR, currentYear)
        calendar.set(Calendar.MONTH, currentMonth)
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val startDate = dateFormatter.format(calendar.time)

        calendar.add(Calendar.MONTH, 1)
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        val endDate = dateFormatter.format(calendar.time)

        return listOf(startDate, endDate)
    }

    fun getCurrentWeek(): Int {
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.minimalDaysInFirstWeek = 4
        return calendar.get(Calendar.WEEK_OF_YEAR)
    }

}