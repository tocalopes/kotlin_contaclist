package com.lopes.newcontactlist.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lopes.newcontactlist.R
import kotlinx.android.synthetic.main.content_lista.view.*

class ListaAdapter (nameList: List<String>, internal var ctx: Context): RecyclerView.Adapter<ListaAdapter.ViewHolder>() {

    internal var nameList: List<String> = ArrayList<String>()
    init {
        this.nameList = nameList
    }

    // Aqui é onde o viewholder é criado a partir do layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.content_lista, parent, false)
        return ViewHolder(view)
    }

    // Nessa parte é onde se modifica o item do viewholder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = nameList[position]

        holder.name.text = name
        if(position % 2 == 0) holder.name.setBackgroundColor(Color.GRAY)
        else holder.name.setBackgroundColor(Color.WHITE)
    }

    // Devolve quantidade de itens do nameList
    override fun getItemCount(): Int {
        return nameList.size
    }

    // Aqui é a criação dos itens do viewholder
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var name = view.tvAdpNome
    }
}