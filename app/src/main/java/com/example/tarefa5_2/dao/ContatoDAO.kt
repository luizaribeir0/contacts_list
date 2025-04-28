package com.example.tarefa5_2.dao

import android.content.ContentValues
import android.content.Context
import com.example.tarefa5_2.database.DatabaseHelper.Companion.COLUNA_ID
import com.example.tarefa5_2.database.DatabaseHelper.Companion.COLUNA_NOME
import com.example.tarefa5_2.database.DatabaseHelper.Companion.COLUNA_OBS
import com.example.tarefa5_2.database.DatabaseHelper.Companion.COLUNA_TELEFONE
import com.example.tarefa5_2.database.DatabaseHelper.Companion.NOME_TABELA_CONTATOS
import com.example.tarefa5_2.model.Contato
import com.example.tarefa5_2.database.DatabaseHelper

class ContatoDAO(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun insertContato(nome: String, telefone: String, observacao: String): Boolean {
        val db = dbHelper.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUNA_NOME, nome)
        contentValues.put(COLUNA_TELEFONE, telefone)
        contentValues.put(COLUNA_OBS, observacao)
        val result = db.insert(NOME_TABELA_CONTATOS, null, contentValues)
        return result != -1L
    }

    fun updateContato(id: Int, nome: String, telefone: String, observacao: String): Boolean {
        val db = dbHelper.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUNA_NOME, nome)
        contentValues.put(COLUNA_TELEFONE, telefone)
        contentValues.put(COLUNA_OBS, observacao)
        val result = db.update(NOME_TABELA_CONTATOS, contentValues, "$COLUNA_ID = ?", arrayOf(id.toString()))
        return result > 0
    }

    fun deleteContato(id: Int): Boolean {
        val db = dbHelper.writableDatabase
        val result = db.delete(NOME_TABELA_CONTATOS, "$COLUNA_ID = ?", arrayOf(id.toString()))
        return result > 0
    }

    fun listarContatos(): List<Contato> {
        val contatos = mutableListOf<Contato>()
        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery("SELECT * FROM $NOME_TABELA_CONTATOS", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(COLUNA_ID))
                val nome = cursor.getString(cursor.getColumnIndex(COLUNA_NOME))
                val telefone = cursor.getString(cursor.getColumnIndex(COLUNA_TELEFONE))
                val observacao = cursor.getString(cursor.getColumnIndex(COLUNA_OBS))

                val contato = Contato(id, nome, telefone, observacao)
                contatos.add(contato)
            } while (cursor.moveToNext())
        }
        cursor.close()

        return contatos
    }
}