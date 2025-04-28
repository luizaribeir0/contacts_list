package com.example.tarefa5_2

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.tarefa5_2.dao.ContatoDAO
import com.example.tarefa5_2.model.Contato

class ContatoAdapter(
    private val context: Context,
    private val contatos: MutableList<Contato>,
    private val contatoDAO: ContatoDAO,
    private val onListaAtualizada: () -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = contatos.size
    override fun getItem(position: Int): Any = contatos[position]
    override fun getItemId(position: Int): Long = contatos[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_contato, parent, false)

        val txtNome = view.findViewById<TextView>(R.id.txtNome)
        val txtTelefone = view.findViewById<TextView>(R.id.txtTelefone)
        val txtObs = view.findViewById<TextView>(R.id.txtObs)
        val btnEditar = view.findViewById<Button>(R.id.btnEditar)
        val btnRemover = view.findViewById<Button>(R.id.btnRemover)

        val contato = contatos[position]

        txtNome.text = contato.nome
        txtTelefone.text = contato.telefone
        txtObs.text = contato.obs

        btnRemover.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Remover")
                .setMessage("Deseja remover este contato?")
                .setPositiveButton("Sim") { _, _ ->
                    contatoDAO.deleteContato(contato.id)
                    onListaAtualizada()
                }
                .setNegativeButton("Não", null)
                .show()
        }

        btnEditar.setOnClickListener {
            val layout = LayoutInflater.from(context)
                .inflate(R.layout.dialog_editar_contato, null)

            val editNome = layout.findViewById<EditText>(R.id.editDialogNome)
            val editTelefone = layout.findViewById<EditText>(R.id.editDialogTelefone)
            val editObs = layout.findViewById<EditText>(R.id.editDialogObs)

            editNome.setText(contato.nome)
            editTelefone.setText(contato.telefone)
            editObs.setText(contato.obs)

            AlertDialog.Builder(context)
                .setTitle("Editar Contato")
                .setView(layout)
                .setPositiveButton("Salvar") { _, _ ->
                    val novoNome = editNome.text.toString()
                    val novoTel = editTelefone.text.toString()
                    val novaObs = editObs.text.toString()
                    contatoDAO.updateContato(contato.id, novoNome, novoTel, novaObs)
                    onListaAtualizada()
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }

        return view
    }
}
