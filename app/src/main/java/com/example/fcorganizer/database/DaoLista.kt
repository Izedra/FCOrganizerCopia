package com.example.fcorganizer.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.fcorganizer.pojos.Listas
import com.example.fcorganizer.pojos.Resultado

@Dao
interface DaoLista{

    // Saca todas las listas
    @Query("SELECT * FROM Listas")
    fun getListas(): List<Listas>

    // Inserta una nueva lista sacando los datos Nombre, Servidor y Avatar del personaje que la crea por pantalla
    // (objeto Resultado obtenido en la primera consulta para obtener la id del personaje que la crea y de el obtener
    // los miembros de su FC y su lista de amigos
    @Insert
    fun insertLista(res: Resultado): Long

    // Borra una lista pasandole su Id (autogenerada)
    @Delete
    fun deleteLista(id: Int)

}