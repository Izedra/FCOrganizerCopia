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
import kotlinx.android.synthetic.main.card_crear_lista.view.*

class AdaptadorVerListas(private val context: Context, private val listado: List<Listado>) : RecyclerView.Adapter<AdaptadorVerListas.ViewHolder>() {
    var checkedChars: SparseBooleanArray = SparseBooleanArray()
    private var sortitems = listado.sortedWith(compareBy{it.nombre})

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.card_crear_lista,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listado.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = sortitems[position]

        val card = holder.card

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netinfo = cm.activeNetworkInfo
        if (netinfo != null && netinfo.isConnected) {
            Glide.with(context).load(item.avatar).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                .apply(RequestOptions.circleCropTransform()).override(100).into(holder.card.char_avatar)
        } else {
            Glide.with(context).load(item.avatar).apply(RequestOptions.circleCropTransform()).override(100).into(holder.card.char_avatar)
        }

        card.tv_charname.text = item.nombre
        card.tv_charserver.text = item.servidor
        holder.card.char_check.isChecked = checkedChars.get(position, false)
        holder.card.char_check.setOnClickListener{
            if (!checkedChars.get(position, false)){
                holder.card.char_check.isChecked = true
                checkedChars.put(position, true)
            } else {
                checkedChars.delete(position)
            }
        }
    }

    class ViewHolder(item: View): RecyclerView.ViewHolder(item){
        val card = item
    }

}
