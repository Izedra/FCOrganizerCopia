package com.example.fcorganizer.adaptadores

import android.content.Context
import android.net.ConnectivityManager
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.fcorganizer.R
import com.example.fcorganizer.pojos.Listado
import com.example.fcorganizer.pojos.Resultado
import kotlinx.android.synthetic.main.card_crear_lista.view.*
import kotlinx.coroutines.newSingleThreadContext

// RV para crear listas, señala a card_crear_lista
class AdaptadorCrearLista(private val items: ArrayList<Resultado>, val context: Context, private val id: Int, private val listados: ArrayList<Listado>): RecyclerView.Adapter<AdaptadorCrearLista.PersonajeVH>() {
    var checkedChars: SparseBooleanArray = SparseBooleanArray()
    private val sortitems = items.sortedWith(compareBy{it.Name})

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonajeVH {
        return PersonajeVH(
            LayoutInflater.from(context).inflate(
                R.layout.card_crear_lista,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PersonajeVH, position: Int) {
        val item = sortitems[position]

        Glide.with(context).load(item.Avatar.toString()).apply(RequestOptions.circleCropTransform()).override(100).into(holder.card.char_avatar)

        holder.card.tv_charname.text = item.Name
        holder.card.tv_charserver.text = item.Server

        holder.card.char_check.isChecked = checkedChars.get(position, false)

        holder.card.char_check.setOnClickListener {
            val listado = Listado(id, item.Name!!, item.Server!!, item.Avatar.toString())
            if (!checkedChars.get(position, false)) {
                holder.card.char_check.isChecked = true
                checkedChars.put(position, true)
                listados.add(listado)
            } else {
                checkedChars.delete(position)
                listados.remove(listado)
            }
        }
    }

    class PersonajeVH(itemview: View) : RecyclerView.ViewHolder(itemview){

        val card = itemview

    }
}