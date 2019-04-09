package com.example.fcorganizer.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fcorganizer.R
import com.example.fcorganizer.database.BaseDatos
import com.example.fcorganizer.pojos.Listas
import kotlinx.android.synthetic.main.card_listas_creadas.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AdaptadorListasCreadas(private val context: Context, private val items: ArrayList<Listas>): RecyclerView.Adapter<AdaptadorListasCreadas.VHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        return VHolder(
            LayoutInflater.from(context).inflate(R.layout.card_listas_creadas, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        val item = items[position]

        Glide.with(context).load(item.avatar).apply(RequestOptions.circleCropTransform()).override(100).into(holder.card.imagen_lista)
        holder.card.tv_lista_nombre.text = item.identificador
        holder.card.tv_nombre_hostchar.text = item.nombre
        holder.card.tv_server_lista.text = item.servidor
        holder.card.tv_lista_id.text = item.idLista.toString()

        GlobalScope.launch {
            holder.card.count_lista.text = BaseDatos(context).daoListado().getListados(item.idLista).toString()
        }

        /* CREAR ONCLICKLISTENER PARA LOS BOTONES*/
    }

    class VHolder(itemview: View): RecyclerView.ViewHolder(itemview){
        val card = itemview
    }
}