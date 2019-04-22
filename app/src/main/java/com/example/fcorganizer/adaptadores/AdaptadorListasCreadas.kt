package com.example.fcorganizer.adaptadores

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.fcorganizer.ListasCreadasDirections
import com.example.fcorganizer.R
import com.example.fcorganizer.database.BaseDatos
import com.example.fcorganizer.pojos.Listas
import kotlinx.android.synthetic.main.card_listas_creadas.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AdaptadorListasCreadas(
    private val context: Context,
    private val items: ArrayList<Listas>
): RecyclerView.Adapter<AdaptadorListasCreadas.VHolder>() {

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

        Glide.with(context).load(item.avatar).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).skipMemoryCache(true).apply(RequestOptions.circleCropTransform()).override(100).into(holder.card.imagen_lista)
        holder.card.tv_lista_nombre.text = item.identificador
        holder.card.tv_nombre_hostchar.text = item.nombre
        holder.card.tv_server_lista.text = item.servidor

        var count = 0
        val t = Thread{
            count = BaseDatos(context).daoListado().getListados(item.idLista)
        }
        t.start()
        t.join()

        holder.card.count_lista.text = "Tamaño: $count personajes"


        holder.card.b_verLista.setOnClickListener {
            Navigation.findNavController(it).navigate(ListasCreadasDirections.actionListasCreadasToVerLista(item.idLista))
        }

        holder.card.b_borrarLista.setOnClickListener {
            dialogBorrar(item, position)
        }

        holder.card.b_editarLista.setOnClickListener {
            Navigation.findNavController(it).navigate(ListasCreadasDirections.actionListasCreadasToEditarLista(item.nombre, item.servidor, item.idLista))
        }

        /* CREAR ONCLICKLISTENER PARA LOS BOTONES*/
    }

    fun dialogBorrar(lista: Listas, position: Int){
        val builder = AlertDialog.Builder(context)


        builder.setTitle("¿Realmente desea borrar la lista?")

        // set up the ok button
        builder.setPositiveButton(android.R.string.ok) { dialog, p1 ->
            GlobalScope.launch {
                BaseDatos(context).daoLista().deleteLista(lista)

            }
            items.removeAt(position)
            notifyDataSetChanged()
            dialog.cancel()
        }

        builder.setNegativeButton(android.R.string.cancel) { dialog, p1 ->
            dialog.cancel()
        }

        builder.show()

    }

    class VHolder(itemview: View): RecyclerView.ViewHolder(itemview){
        val card = itemview
    }
}