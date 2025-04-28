package com.example.tarefa5_2.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "contatos.db"
        const val DATABASE_VERSION = 1
        const val NOME_TABELA_CONTATOS = "contatos"
        const val COLUNA_ID = "id"
        const val COLUNA_NOME = "nome"
        const val COLUNA_TELEFONE = "telefone"
        const val COLUNA_OBS = "observacao"
    }
        
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $NOME_TABELA_CONTATOS ("
                + "$COLUNA_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUNA_NOME TEXT,"
                + "$COLUNA_TELEFONE TEXT,"
                + "$COLUNA_OBS TEXT)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $NOME_TABELA_CONTATOS")
        onCreate(db)
    }
}
