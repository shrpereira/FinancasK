package br.com.shrpereira.financask.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import br.com.shrpereira.financask.R
import br.com.shrpereira.financask.extension.formatToBrazilianCurrency
import br.com.shrpereira.financask.extension.formatToBrazilianDate
import br.com.shrpereira.financask.extension.limitaEmAte
import br.com.shrpereira.financask.model.Tipo
import br.com.shrpereira.financask.model.Transacao
import kotlinx.android.synthetic.main.transacao_item.view.*

class ListaTransacoesAdapter(private val transacoes: List<Transacao>,
                             private val context: Context) : BaseAdapter() {

    private val limiteDaCategoria = 14

    @SuppressLint("ViewHolder")
    override fun getView(posicao: Int, view: View?, viewGroup: ViewGroup?): View {
        val inflatedView = LayoutInflater
                .from(context)
                .inflate(R.layout.transacao_item, viewGroup, false)

        val transacao = transacoes[posicao]

        adicionaValor(transacao, inflatedView)
        adicionaIcone(transacao, inflatedView)
        adicionaCategoria(inflatedView, transacao)
        adicionaData(inflatedView, transacao)

        return inflatedView
    }

    private fun adicionaValor(transacao: Transacao, inflatedView: View) {
        val cor: Int = corPor(transacao.tipo)

        inflatedView.transacao_valor.setTextColor(cor)
        inflatedView.transacao_valor.text = transacao.valor.formatToBrazilianCurrency()
    }

    private fun corPor(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA) {
            return ContextCompat.getColor(context, R.color.receita)
        }

        return ContextCompat.getColor(context, R.color.despesa)
    }

    private fun adicionaIcone(transacao: Transacao, inflatedView: View) {
        val icone = iconePor(transacao.tipo)

        inflatedView.transacao_icone.setBackgroundResource(icone)
    }

    private fun iconePor(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA) {
            return R.drawable.icone_transacao_item_receita
        }

        return R.drawable.icone_transacao_item_despesa
    }

    private fun adicionaCategoria(inflatedView: View, transacao: Transacao) {
        inflatedView.transacao_categoria.text = transacao.categoria.limitaEmAte(limiteDaCategoria)
    }

    private fun adicionaData(inflatedView: View, transacao: Transacao) {
        inflatedView.transacao_data.text = transacao.data.formatToBrazilianDate()
    }

    override fun getItem(posicao: Int): Transacao {
        return transacoes[posicao]
    }

    override fun getItemId(posicao: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return transacoes.size
    }
}