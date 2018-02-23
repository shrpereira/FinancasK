package br.com.shrpereira.financask.extension

import java.text.SimpleDateFormat
import java.util.*

private val primeiroCaractere = 0

fun String.limitaEmAte(caracteres: Int): String {
    if (this.length > caracteres) {
        return "${this.substring(primeiroCaractere, caracteres)}..."
    }
    return this
}

fun String.converteParaCalendar(): Calendar {
    val formatoBrasileiro = SimpleDateFormat("dd/MM/yyyy")
    val dataConvertida = formatoBrasileiro.parse(this)
    val data = Calendar.getInstance()
    data.time = dataConvertida
    return data
}