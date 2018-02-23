package br.com.shrpereira.financask.extension

import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.*

fun BigDecimal.formatToBrazilianCurrency(): String {
    val formatoBrasileiro = DecimalFormat
            .getCurrencyInstance(Locale("pt", "br"))

    return formatoBrasileiro.format(this)
            .replace("R$", "R$ ")
            .replace("-R$ ", "R$ -")
}