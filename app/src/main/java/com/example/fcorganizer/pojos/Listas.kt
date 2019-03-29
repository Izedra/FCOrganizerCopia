package com.example.fcorganizer.pojos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Listas (
    @PrimaryKey(autoGenerate = true) val idLista: Int,
    val Personaje: Resultado
)