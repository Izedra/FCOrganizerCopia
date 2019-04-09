package com.example.fcorganizer.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.fcorganizer.pojos.Listado
import com.example.fcorganizer.pojos.PersonajeP
import com.example.fcorganizer.pojos.Resultado

@Dao
interface DaoListado {

    /*val idListado: Int,
    val Nombre: String,
    val Servidor: String,
    val Avatar: String*/

    // Saca todos los personajes de una lista, pasandole el id de esta
    @Query("SELECT * FROM Listado WHERE idListado = :id")
    fun getListado(id: Int): List<Listado>

    @Query("SELECT COUNT(*) FROM Listado WHERE idListado = :id")
    fun getListados(id: Int): Int

    // Inserta un personaje
    @Insert
    fun insertListado(listado: Listado)

    @Delete
    fun borrarListado(listacompleta: List<Listado>)

    @Delete
    fun borrarPersonaje(personaje: Listado)
}