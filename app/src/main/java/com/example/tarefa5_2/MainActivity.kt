package com.example.tarefa5_2

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.tarefa5_2.model.Contato
import com.example.tarefa5_2.dao.ContatoDAO

class MainActivity : AppCompatActivity() {

    private lateinit var editNome: EditText
    private lateinit var editTelefone: EditText
    private lateinit var editObs: EditText
    private lateinit var btnSalvar: Button
    private lateinit var listView: ListView

    private lateinit var contatoDAO: ContatoDAO
    private lateinit var adapter: ContatoAdapter
    private var listaContatos = mutableListOf<Contato>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editNome = findViewById(R.id.editNome)
        editTelefone = findViewById(R.id.editTelefone)
        editObs = findViewById(R.id.editObservacao)
        btnSalvar = findViewById(R.id.btnSalvar)
        listView = findViewById(R.id.listaContatos)

        contatoDAO = ContatoDAO(this)
        atualizarLista()

        btnSalvar.setOnClickListener {
            val nome = editNome.text.toString()
            val telefone = editTelefone.text.toString()
            val obs = editObs.text.toString()

            if (nome.isNotBlank()) {
                contatoDAO.insertContato(nome, telefone, obs)
                editNome.setText("")
                editTelefone.setText("")
                editObs.setText("")
                atualizarLista()
            } else {
                Toast.makeText(this, "Preencha o nome", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun atualizarLista() {
        listaContatos = contatoDAO.listarContatos().toMutableList()
        adapter = ContatoAdapter(this, listaContatos, contatoDAO) {
            atualizarLista()
        }
        listView.adapter = adapter
    }
}
