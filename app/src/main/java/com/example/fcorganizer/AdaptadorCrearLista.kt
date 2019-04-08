package com.example.fcorganizer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fcorganizer.pojos.Resultado
import kotlinx.android.synthetic.main.card_crear_lista.view.*

// RV para crear listas, señala a card_crear_lista
class AdaptadorCrearLista(private val items: ArrayList<Resultado>, val context: Context): RecyclerView.Adapter<AdaptadorCrearLista.PersonajeVH>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PersonajeVH, position: Int) {
        Glide.with(context).load(items[position].Avatar).apply(RequestOptions.circleCropTransform()).override(100).into(holder.card.char_avatar)
        holder.card.tv_charname.text = items[position].Name
        holder.card.tv_charserver.text = items[position].Server
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonajeVH {
        return PersonajeVH(LayoutInflater.from(context).inflate(R.layout.card_crear_lista, parent, false))
    }

    class PersonajeVH(itemview: View) : RecyclerView.ViewHolder(itemview){
        val card = itemview
    }
}