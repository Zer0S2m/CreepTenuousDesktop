package com.zer0s2m.creeptenuous.desktop.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import com.zer0s2m.creeptenuous.desktop.ui.components.misc.daysCount
import com.zer0s2m.creeptenuous.desktop.ui.components.misc.monthName
import java.text.SimpleDateFormat
import java.util.*

/**
 * Date picker component.
 *
 * @param initDate Start date.
 * @param onDateSelect Action that will be performed when the date is selected.
 * @param onDismissRequest Cancel date selection.
 * @param minYear Minimum number for year selection.
 * @param maxYear Maximum number for year selection.
 */
@Composable
fun DatePicker(
    initDate: Date = Date(),
    onDateSelect: (Date) -> Unit,
    onDismissRequest: () -> Unit,
    minYear: Int = GregorianCalendar().get(Calendar.YEAR) - 10,
    maxYear: Int = GregorianCalendar().get(Calendar.YEAR) + 10
) {
    val calendar = GregorianCalendar().apply {
        time = initDate
    }

    var year by remember { mutableStateOf(calendar.get(Calendar.YEAR)) }
    var month by remember { mutableStateOf(calendar.get(Calendar.MONTH)) }
    var day by remember { mutableStateOf(calendar.get(Calendar.DAY_OF_MONTH)) }

    Card(
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colors.secondaryVariant)
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = SimpleDateFormat("EEEE, d MMMM yyyy", Locale.ENGLISH)
                        .format(GregorianCalendar(year, month, day).time),
                    style = MaterialTheme.typography.h6
                )
            }
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    MonthSelector(
                        month = month,
                        onValueChange = { month = it }
                    )
                    YearSelector(
                        year = year,
                        onValueChange = { year = it },
                        minYear = minYear,
                        maxYear = maxYear,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                    )
                }

                HeaderDays("D", "L", "M", "M", "J", "V", "S")
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(MaterialTheme.colors.secondaryVariant)
                )

                val startDay = GregorianCalendar(year, month, 1)
                    .apply {
                        firstDayOfWeek = Calendar.SUNDAY
                    }.get(Calendar.DAY_OF_WEEK)

                var render = false
                var dayCounter = 1
                val maxDay = GregorianCalendar(year, month, 1).daysCount()

                if (day > maxDay) {
                    day = maxDay
                }

                for (i in 1..6) {
                    Row {
                        for (j in 1..7) {
                            if (j == startDay) {
                                render = true
                            }
                            if (!render || dayCounter > maxDay) {
                                Day(0, false) { }
                            } else {
                                Day(
                                    day = dayCounter,
                                    selected = (day == dayCounter),
                                    onChangeValue = { day = it }
                                )
                                dayCounter++
                            }
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = { onDismissRequest() },
                    modifier = Modifier
                        .pointerHoverIcon(icon = PointerIcon.Hand)
                ) {
                    Text(text = "Cancel")
                }
                TextButton(
                    onClick = {
                        onDateSelect(GregorianCalendar(year, month, day).time)
                    },
                    modifier = Modifier
                        .pointerHoverIcon(icon = PointerIcon.Hand)
                ) {
                    Text(text = "Select")
                }
            }
        }
    }
}

/**
 * Component for selecting a year from a dropdown list.
 *
 * @param year Current selected year.
 * @param onValueChange Called when the menu item [DropdownMenuItem] was clicked.
 * @param minYear Minimum number for year selection.
 * @param maxYear Maximum number for year selection.
 * @param modifier The modifier to be applied to the [Row].
 */
@Composable
private fun YearSelector(
    year: Int,
    onValueChange: (Int) -> Unit,
    minYear: Int,
    maxYear: Int,
    modifier: Modifier = Modifier
) {
    var expandYearList by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.clickable { expandYearList = true },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = year.toString())
        Spacer(Modifier.width(4.dp))
        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)

        DropdownMenu(
            expanded = expandYearList,
            onDismissRequest = { expandYearList = false }
        ) {
            for (fYear in minYear..maxYear) {
                DropdownMenuItem(onClick = {
                    onValueChange(fYear)
                    expandYearList = false
                }) {
                    Text(text = fYear.toString())
                }
            }
        }
    }
}

/**
 * Component for selecting a month from a drop-down list.
 *
 * @param month Current selected month.
 * @param onValueChange Called when the menu item [DropdownMenuItem] was clicked.
 */
@Composable
private fun MonthSelector(
    month: Int,
    onValueChange: (Int) -> Unit
) {
    var expandMonthList by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.clickable { expandMonthList = true },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = monthName(month).uppercase())
        Spacer(Modifier.width(4.dp))
        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)

        DropdownMenu(
            expanded = expandMonthList,
            onDismissRequest = { expandMonthList = false }
        ) {
            for (m in Calendar.JANUARY..Calendar.DECEMBER) {
                DropdownMenuItem(onClick = {
                    onValueChange(m)
                    expandMonthList = false
                }) {
                    Text(text = monthName(m).uppercase())
                }
            }
        }
    }
}

/**
 * Component for drawing the top panel when displaying days.
 *
 * @param daysNames Name of days.
 */
@Composable
private fun HeaderDays(vararg daysNames: String) {
    Row {
        for (dayName in daysNames) {
            DayName(dayName)
        }
    }
}

/**
 * Component for rendering the day when it is selected from the list.
 *
 * @param day Index of the day.
 * @param selected Is any day currently selected.
 * @param onChangeValue Configure component to receive clicks via input or accessibility "click" event.
 */
@Composable
private fun Day(
    day: Int,
    selected: Boolean,
    onChangeValue: (Int) -> Unit
) {
    val clickable = day != 0

    Box(
        modifier = Modifier
            .size(48.dp)
            .padding(3.dp)
            .clip(RoundedCornerShape(50))
            .background(
                if (selected) {
                    MaterialTheme.colors.secondaryVariant
                } else {
                    MaterialTheme.colors.surface
                }
            ).clickable(enabled = clickable) { onChangeValue(day) },
        contentAlignment = Alignment.Center
    ) {
        if (day != 0) {
            Text(text = day.toString())
        }
    }
}

/**
 * Rendering component Day name.
 *
 * @param day Day as a name.
 */
@Composable
private fun DayName(day: String) {
    Box(
        modifier = Modifier.size(48.dp)
            .padding(3.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = day)
    }
}

/**
 * Modal window for selecting a date.
 *
 * @param initDate Initial selected date state.
 * @param expandedState Modal window states.
 * @param onDismissRequest Cancel date selection.
 * @param onDateSelect Action that will be performed when the date is selected.
 */
@Composable
fun ModalSelectDate(
    initDate: MutableState<Date>,
    expandedState: MutableState<Boolean>,
    onDismissRequest: () -> Unit,
    onDateSelect: (Date) -> Unit
) {
    ModalPopup(
        stateModal = expandedState,
        modifierLayout = Modifier
            .width(350.dp)
            .height(520.dp),
        modifierLayoutContent = Modifier
            .fillMaxSize()
    ) {
        DatePicker(
            initDate = initDate.value,
            onDismissRequest = onDismissRequest,
            onDateSelect = onDateSelect,
            minYear = GregorianCalendar().get(Calendar.YEAR)
        )
    }
}
