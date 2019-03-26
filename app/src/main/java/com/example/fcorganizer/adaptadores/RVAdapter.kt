package com.example.fcorganizer.adaptadores

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.fcorganizer.R
import com.example.fcorganizer.pojos.Resultado
import com.squareup.picasso.Picasso

class RVAdapter(var items: ArrayList<Resultado>): RecyclerView.Adapter<RVAdapter.ViewHolder>(){

    init{

    }


    override fun getItemCount(): Int {
        return this.items.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVAdapter.ViewHolder{
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.recycler_tarjeta, parent, false)

        return ViewHolder(vista)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.titulo?.text = item.toString()
        //Picasso.with(activity).load(item?.poster).into(holder.poster)

    }


    class ViewHolder(vista: View): RecyclerView.ViewHolder(vista){

        var poster: ImageView? = null

        var titulo: TextView? = null

        init {
        }

    }

}