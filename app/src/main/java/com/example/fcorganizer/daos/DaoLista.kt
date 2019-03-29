package com.example.fcorganizer.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fcorganizer.pojos.Listas
import com.example.fcorganizer.pojos.Resultado

@Dao
interface DaoLista{

    // Saca todas las listas
    @Query("SELECT * FROM Listas")
    fun getListas(): List<Listas>

    // Saca todos los personajes de una lista, pasandole el id de esta
    @Query("SELECT Personaje FROM Listado WHERE idListado = :id")
    fun getListado(id: Int): List<Resultado>

    @Insert
    fun insertLista(): Long


}