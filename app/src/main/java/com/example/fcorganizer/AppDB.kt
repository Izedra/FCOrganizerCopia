package com.example.fcorganizer

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fcorganizer.daos.DaoLista
import com.example.fcorganizer.daos.DaoListado
import com.example.fcorganizer.pojos.Listado
import com.example.fcorganizer.pojos.Listas

@Database(entities = [Listas::class, Listado::class ], version = 1, exportSchema = false)
abstract class AppDB: RoomDatabase() {

    abstract fun daoLista(): DaoLista
    abstract fun daoListado(): DaoListado
}