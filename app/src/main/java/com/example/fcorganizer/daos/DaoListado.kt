package com.example.fcorganizer.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.fcorganizer.pojos.Resultado

@Dao
interface DaoListado {

    // Saca todos los personajes de una lista, pasandole el id de esta
    @Query("SELECT Personaje FROM Listado WHERE idListado = :id")
    fun getListado(id: Int): List<Resultado>

    // Inserta todos los personajes seleccionados, precedidos del id de la lista que se crea
    @Insert
    fun insertListado(listado: List<Pair<Int, Resultado>>)

    @Delete
    fun borrarListado(listado: List<Pair<Int, Resultado>>)
}