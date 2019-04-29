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
import com.example.fcorganizer.database.BaseDatos
import com.example.fcorganizer.pojos.Listado
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.card_crear_lista.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AdaptadorEditarLista(
    private val items: ArrayList<Listado>,
    private val context: Context,
    private val listaexistente: ArrayList<Listado>
): RecyclerView.Adapter<AdaptadorEditarLista.ViewHolder>() {

    var checkedChars: SparseBooleanArray = SparseBooleanArray()
    private val sortitems = items.sortedWith(compareBy{it.nombre})

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorEditarLista.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.card_crear_lista, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: AdaptadorEditarLista.ViewHolder, position: Int) {
        val item = sortitems[position]

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netinfo = cm.activeNetworkInfo
        if (netinfo != null && netinfo.isConnected) {
            Glide.with(context).load(item.avatar).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                .apply(RequestOptions.circleCropTransform()).override(100).into(holder.avatr)
        } else {
            Glide.with(context).load(item.avatar).apply(RequestOptions.circleCropTransform()).override(100).into(holder.avatr)
        }

        holder.nombrer.text = item.nombre
        holder.servidorr.text = item.servidor

        if (listaexistente.contains(item)){
            checkedChars.put(position, true)
        }

        holder.cbr.isChecked = checkedChars.get(position, false)

        holder.cbr.setOnClickListener{
            if (!checkedChars.get(position, false)){
                holder.cbr.isChecked = true
                checkedChars.put(position, true)

                GlobalScope.launch {
                    BaseDatos(context).daoListado().insertListado(item)
                    FirebaseDatabase.getInstance().reference.child(item.idListado.toString()).child(item.nombre+item.servidor).setValue(item)
                }
            } else {
                checkedChars.delete(position)
                GlobalScope.launch {
                    BaseDatos(context).daoListado().borrarPersonaje(item)
                    FirebaseDatabase.getInstance().reference.child(item.idListado.toString()).child(item.nombre+item.servidor).removeValue()
                }
            }
        }
    }

    class ViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
        private val card = itemview

        val avatr = card.char_avatar!!
        val nombrer = card.tv_charname!!
        val servidorr = card.tv_charserver!!
        val cbr = card.char_check!!
    }
}