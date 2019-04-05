package com.example.fcorganizer.pojos

import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["idListado", "nombre", "servidor"], foreignKeys = [
    ForeignKey(
        entity = Listas::class,
        parentColumns = ["idLista"],
        childColumns = ["idListado"],
        onDelete = CASCADE
    )]
)
data class Listado(
    val idListado: Int,
    val nombre: String,
    val servidor: String,
    val avatar: String

)

@Entity
data class Listas (
    // Los atugenerados simplemente se pasa con un 0 al insertar
    @PrimaryKey(autoGenerate = true) val idLista: Int,
    val identificador: String,
    val nombre: String,
    val servidor: String,
    val avatar: String
)


/*data class Resultado (
            val Avatar: URL?,
            val FeastMatches: Int?,
            val ID: Int?,
            val Name: String?,
            val Rank: Any?,
            val RankIcon: URL?,
            val Server: String?
        )*/