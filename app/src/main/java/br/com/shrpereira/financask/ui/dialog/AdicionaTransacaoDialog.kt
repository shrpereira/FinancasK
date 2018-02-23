package br.com.shrpereira.financask.ui.dialog

import android.content.Context
import android.view.ViewGroup
import br.com.shrpereira.financask.R
import br.com.shrpereira.financask.model.Tipo

class AdicionaTransacaoDialog(viewGroup: ViewGroup,
                              context: Context) : FormularioTransacaoDialog(context, viewGroup) {

    override val tituloBotaoPositivo: String
        get() = "Adicionar"

    override fun tituloPor(tipoTransacao: Tipo): Int {
        if (tipoTransacao == Tipo.RECEITA) {
            return R.string.adiciona_receita
        }
        return R.string.adiciona_despesa
    }
}