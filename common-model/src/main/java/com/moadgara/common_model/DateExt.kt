package com.moadgara.common_model

import java.util.Calendar
import java.util.Calendar.getInstance

fun Calendar.currentYear(): Int = getInstance().get(Calendar.YEAR)

fun Calendar.currentWeek(): Int = getInstance().get(Calendar.WEEK_OF_YEAR)