package com.example.fcorganizer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fcorganizer.pojos.Listado
import com.example.fcorganizer.pojos.Listas

@Database(entities = [Listas::class, Listado::class], version = 1, exportSchema = false)
abstract class BaseDatos: RoomDatabase() {
    abstract fun daoLista(): DaoLista
    abstract fun daoListado(): DaoListado

    companion object {
        @Volatile private var instance: BaseDatos? = null
        private val LOCK = Any()

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context, BaseDatos::class.java, "ff-xiv.db").build()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it }
        }


    }
}