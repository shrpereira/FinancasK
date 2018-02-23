package br.com.shrpereira.financask.ui.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import br.com.shrpereira.financask.R
import br.com.shrpereira.financask.extension.converteParaCalendar
import br.com.shrpereira.financask.extension.formatToBrazilianDate
import br.com.shrpereira.financask.model.Tipo
import br.com.shrpereira.financask.model.Transacao
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

abstract class FormularioTransacaoDialog(private val context: Context,
                                         private val viewGroup: ViewGroup) {

    private val dialogLayout = criaLayout()
    protected val campoValor = dialogLayout.form_transacao_valor
    protected val campoData = dialogLayout.form_transacao_data
    protected val campoCategoria = dialogLayout.form_transacao_categoria
    protected abstract val tituloBotaoPositivo: String

    fun chama(tipoTransacao: Tipo, delegate: (transacao: Transacao) -> Unit) {
        configuraCampoData()
        configuraCampoCategoria(tipoTransacao)
        configuraFormulario(tipoTransacao, delegate)
    }

    private fun configuraFormulario(tipoTransacao: Tipo, delegate: (transacao: Transacao) -> Unit) {
        val titulo = tituloPor(tipoTransacao)

        AlertDialog.Builder(context)
                .setTitle(titulo)
                .setView(dialogLayout)
                .setPositiveButton(tituloBotaoPositivo, { _, _ ->
                    val valorEmTexto = campoValor.text.toString()
                    val dataEmTexto = campoData.text.toString()
                    val categoriaEmTexto = campoCategoria.selectedItem.toString()

                    val valor = converteCampoValor(valorEmTexto)
                    val data = dataEmTexto.converteParaCalendar()

                    val transacaoCriada = Transacao(
                            tipo = tipoTransacao,
                            valor = valor,
                            data = data,
                            categoria = categoriaEmTexto)

                    delegate(transacaoCriada)
                })
                .setNegativeButton("Cancelar", null)
                .show()
    }

    protected abstract fun tituloPor(tipoTransacao: Tipo): Int

    private fun converteCampoValor(valorEmTexto: String): BigDecimal {
        return try {
            BigDecimal(valorEmTexto)
        } catch (exception: NumberFormatException) {
            Toast.makeText(context, "Falha na conversão de valor", Toast.LENGTH_LONG).show()
            BigDecimal.ZERO
        }
    }

    private fun configuraCampoCategoria(tipoTransacao: Tipo) {
        val categorias = categoriasPor(tipoTransacao)
        val adapter = ArrayAdapter.createFromResource(
                context,
                categorias,
                android.R.layout.simple_spinner_dropdown_item)

        campoCategoria.adapter = adapter
    }

    protected fun categoriasPor(tipoTransacao: Tipo): Int {
        if (tipoTransacao == Tipo.RECEITA) {
            return R.array.categorias_de_receita
        }
        return R.array.categorias_de_despesa
    }

    private fun configuraCampoData() {
        val hoje = Calendar.getInstance()
        val ano = hoje.get(Calendar.YEAR)
        val mes = hoje.get(Calendar.MONTH)
        val dia = hoje.get(Calendar.DAY_OF_MONTH)

        campoData.setText(hoje.formatToBrazilianDate())
        campoData.setOnClickListener {
            DatePickerDialog(context, { _, ano, mes, dia ->
                val dataSelecionada = Calendar.getInstance()
                dataSelecionada.set(ano, mes, dia)
                campoData.setText(dataSelecionada.formatToBrazilianDate())
            }, ano, mes, dia).show()
        }
    }

    private fun criaLayout(): View {
        return LayoutInflater.from(context)
                .inflate(R.layout.form_transacao,
                        viewGroup,
                        false)
    }
}