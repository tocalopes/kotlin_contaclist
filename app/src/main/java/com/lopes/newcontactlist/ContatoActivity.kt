package com.lopes.newcontactlist

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lopes.newcontactlist.db.DatabaseHandler
import com.lopes.newcontactlist.model.Contato
import kotlinx.android.synthetic.main.activity_contato.*

class ContatoActivity : AppCompatActivity() {

    // Database
    val databaseHandler = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contato)

        val edit = intent.getBooleanExtra("edit", false)
        val position = intent.getIntExtra("position", 0)
        if(edit){
            val contato = databaseHandler.getContato(position)
            edit_nome.setText(contato.nome)
            edit_email.setText(contato.email)
            edit_telefone.setText(contato.telefone)
            btnInsertContato.setText("Editar")
        }
        btnInsertContato.setOnClickListener {
            if(edit_nome.text.toString() == ""){
                Toast.makeText(this,"Nome está vazio.",Toast.LENGTH_SHORT).show()
            }else if(edit_email.text.toString() == ""){
                Toast.makeText(this,"Email está vazio.",Toast.LENGTH_SHORT).show()
            }else if(edit_telefone.text.toString() == ""){
                Toast.makeText(this,"Telefone está vazio.",Toast.LENGTH_SHORT).show()
            }
            else {
                if(edit){
                    val contato = Contato(position, edit_nome.text.toString(),edit_email.text.toString(),edit_telefone.text.toString())
                    databaseHandler.updateContato(contato)
                    finish()
                }
                else {
                    val contato = Contato(0, edit_nome.text.toString(),edit_email.text.toString(),edit_telefone.text.toString())
                    databaseHandler.addContato(contato)
                    finish()
                }
            }
        }
        btnCancel.setOnClickListener {
            finish()
        }
    }
}