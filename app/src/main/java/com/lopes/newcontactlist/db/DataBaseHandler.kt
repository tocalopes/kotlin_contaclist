package com.lopes.newcontactlist.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.lopes.newcontactlist.model.Contato

class DatabaseHandler (ctx: Context): SQLiteOpenHelper(ctx,DB_NAME,null,DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY,"+
                    "$NAME TEXT, $EMAIL TEXT, $PHONE TEXT);"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun addContato(contato: Contato): Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME, contato.nome)
        values.put(EMAIL, contato.email)
        values.put(PHONE, contato.telefone)
        val _success = db.insert(TABLE_NAME,null,values)
        return (("$_success").toInt() != -1)
    }

    fun getContato(_id: Int): Contato {
        val contato = Contato()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $ID = $_id"
        val cursor = db.rawQuery(selectQuery, null)
        cursor?.moveToFirst()
        contato.id = cursor.getInt(cursor.getColumnIndex(ID))
        contato.nome = cursor.getString(cursor.getColumnIndex(NAME))
        contato.email = cursor.getString(cursor.getColumnIndex(EMAIL))
        contato.telefone = cursor.getString(cursor.getColumnIndex(PHONE))
        cursor.close()
        return contato
    }

    fun contatos(): ArrayList<Contato> {
        val contatoList = ArrayList<Contato>()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    val contato = Contato()
                    contato.id = cursor.getInt(cursor.getColumnIndex(ID))
                    contato.nome = cursor.getString(cursor.getColumnIndex(NAME))
                    contatoList.add(contato)
                }while(cursor.moveToNext())
            }
        }
        cursor.close()
        return contatoList
    }

    fun updateContato(contato: Contato): Boolean{
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(NAME, contato.nome)
            put(EMAIL, contato.email)
            put(PHONE, contato.telefone)
        }
        val _success = db.update(TABLE_NAME, values, ID + "=?", arrayOf(contato.id.toString())).toLong()
        db.close()
        return ("$_success").toInt() != -1
    }

    fun deleteContato(_id: Int): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, ID + "=?", arrayOf(_id.toString())).toLong()
        return ("$_success").toInt() != -1
    }

    fun deleteAllContato(): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, null,null).toLong()
        db.close()
        return ("$_success").toInt() != -1
    }

    companion object {
        private val DB_VERSION = 1
        private val DB_NAME = "agenda"
        private val TABLE_NAME = "Contato"
        private val ID = "Id"
        private val NAME = "Nome"
        private val EMAIL = "Email"
        private val PHONE = "Phone"
    }
}