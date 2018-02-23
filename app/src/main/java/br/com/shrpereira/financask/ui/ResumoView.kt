package br.com.shrpereira.financask.ui

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import br.com.shrpereira.financask.R
import br.com.shrpereira.financask.extension.formatToBrazilianCurrency
import br.com.shrpereira.financask.model.Resumo
import br.com.shrpereira.financask.model.Transacao
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

class ResumoView(context: Context,
                 private val view: View,
                 transacoes: List<Transacao>) {

    private val resumo: Resumo = Resumo(transacoes)
    private val receitaColor = ContextCompat.getColor(context, R.color.receita)
    private val despesaColor = ContextCompat.getColor(context, R.color.despesa)

    fun atualiza() {
        adicionaReceita()
        adicionaDespesa()
        adicionaTotal()
    }

    private fun adicionaReceita() {
        with(view.resumo_card_receita) {
            setTextColor(receitaColor)
            text = resumo.receita.formatToBrazilianCurrency()
        }
    }

    private fun adicionaDespesa() {
        with(view.resumo_card_despesa) {
            setTextColor(despesaColor)
            text = resumo.despesa.formatToBrazilianCurrency()
        }
    }

    private fun adicionaTotal() {
        val total = resumo.total

        with(view.resumo_card_total) {
            text = total.formatToBrazilianCurrency()
            setTextColor(corPor(total))
        }
    }

    private fun corPor(total: BigDecimal): Int {
        return if (total == BigDecimal.ZERO) {
            despesaColor
        } else {
            receitaColor
        }
    }
}