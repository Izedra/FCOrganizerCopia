package com.example.fcorganizer.pojos

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

@Entity(primaryKeys = ["idLista", "Personaje"], foreignKeys = [
    ForeignKey(
        entity = Listas::class,
        parentColumns = ["idLista"],
        childColumns = ["idListado"],
        onDelete = CASCADE
    )]
)
data class Listado(
    val idListado: Int,
    val Personaje: PersonajeP
)