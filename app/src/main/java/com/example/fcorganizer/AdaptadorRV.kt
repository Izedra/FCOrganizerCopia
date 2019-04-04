package com.example.fcorganizer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AdaptadorRV: RecyclerView.Adapter<AdaptadorRV.PersonajesVH>() {

    override fun getItemCount(): Int {
        return 50
    }

    override fun onBindViewHolder(holder: PersonajesVH, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonajesVH {
        return PersonajesVH(LayoutInflater.from(parent.context).inflate(R.layout.fragment_crear_lista_rv, parent, false))
    }

    class PersonajesVH(itemview: View) : RecyclerView.ViewHolder(itemview)
}