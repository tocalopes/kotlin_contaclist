package com.lopes.newcontactlist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.lopes.newcontactlist.adapter.ListaAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var nameList = ArrayList<String>()

    // Iniciando a RecyclerView
    var listaAdapter: ListaAdapter? = null
    var linearLayoutManager: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        btnInsert.setOnClickListener {
            val intent = Intent(this, NameActivity::class.java)
            startActivityForResult(intent,1)
        }
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun initView(){
        listaAdapter = ListaAdapter(nameList, this)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = linearLayoutManager
        recyclerview.adapter = listaAdapter
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            val nome: String = data!!.getStringExtra("nome")
            nameList.add(nome)
        }
    }
}