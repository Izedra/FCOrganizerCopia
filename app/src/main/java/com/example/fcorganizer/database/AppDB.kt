package com.example.fcorganizer.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fcorganizer.database.DaoLista
import com.example.fcorganizer.database.DaoListado
import com.example.fcorganizer.pojos.Listado
import com.example.fcorganizer.pojos.Listas

@Database(entities = [Listas::class, Listado::class ], version = 1, exportSchema = false)
abstract class AppDB: RoomDatabase() {

    abstract fun daoLista(): DaoLista
    abstract fun daoListado(): DaoListado
}