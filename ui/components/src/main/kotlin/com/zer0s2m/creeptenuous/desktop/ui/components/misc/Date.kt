package com.zer0s2m.creeptenuous.desktop.ui.components.misc

import java.util.*

/**
 * Get the number of days in a specified month.
 */
fun GregorianCalendar.daysCount(): Int {
    return when (get(Calendar.MONTH)) {
        Calendar.JANUARY,
        Calendar.MARCH,
        Calendar.MAY,
        Calendar.JULY,
        Calendar.AUGUST,
        Calendar.OCTOBER,
        Calendar.DECEMBER -> 31
        Calendar.FEBRUARY -> if (isLeapYear(get(Calendar.YEAR))) 29 else 28
        Calendar.APRIL,
        Calendar.JUNE,
        Calendar.SEPTEMBER,
        Calendar.NOVEMBER -> 30
        else -> 0
    }
}

/**
 * Get the name of the month by its ordinal month.
 *
 * @param month Serial number of the month.
 */
fun monthName(month: Int): String {
    return when (month) {
        GregorianCalendar.JANUARY -> "January"
        GregorianCalendar.FEBRUARY -> "February"
        GregorianCalendar.MARCH -> "March"
        GregorianCalendar.APRIL -> "April"
        GregorianCalendar.MAY -> "May"
        GregorianCalendar.JUNE -> "June"
        GregorianCalendar.JULY -> "July"
        GregorianCalendar.AUGUST -> "August"
        GregorianCalendar.SEPTEMBER -> "September"
        GregorianCalendar.OCTOBER -> "October"
        GregorianCalendar.NOVEMBER -> "November"
        GregorianCalendar.DECEMBER -> "December"
        else -> "Undefined"
    }
}