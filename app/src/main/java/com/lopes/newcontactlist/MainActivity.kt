package com.lopes.newcontactlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.lopes.newcontactlist.adapter.ListaAdapter
import com.lopes.newcontactlist.db.DatabaseHandler
import com.lopes.newcontactlist.model.Contato
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // Iniciando a RecyclerView
    var listaAdapter: ListaAdapter? = null
    var linearLayoutManager: LinearLayoutManager? = null

    // SQLite
    var contatoList = ArrayList<Contato>()
    var databaseHandler = DatabaseHandler(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        btnInsert.setOnClickListener {
            val intent = Intent(this, ContatoActivity::class.java)
            startActivityForResult(intent,1)
        }
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun initView(){
        contatoList = databaseHandler.contatos()
        listaAdapter = ListaAdapter(contatoList,this, this::deleteAdapter)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = linearLayoutManager
        recyclerview.adapter = listaAdapter
    }
    private fun deleteAdapter(position: Int){
        contatoList.removeAt(position)
        listaAdapter!!.notifyItemRemoved(position)
    }
}
