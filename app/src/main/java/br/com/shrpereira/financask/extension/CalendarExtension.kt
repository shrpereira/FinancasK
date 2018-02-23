package br.com.shrpereira.financask.extension

import java.text.SimpleDateFormat
import java.util.*

fun Calendar.formatToBrazilianDate(): String {
    val df = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return df.format(this.time)
}