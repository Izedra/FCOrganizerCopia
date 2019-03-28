package com.example.fcorganizer.pojos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Lista")
data class Lista (
    @PrimaryKey(autoGenerate = true) val idLista: Int,
    val Personajes: ArrayList<Resultado>
)