package com.example.fcorganizer

import android.content.Context
import androidx.room.*
import com.example.fcorganizer.pojos.Lista
import com.example.fcorganizer.pojos.PersonajeP
import javax.sql.DataSource

@Database(entities = [(Lista::class)], version = 1)
abstract class AppDB: RoomDatabase() {

    abstract fun listaDao(): listaDAO

    companion object {
        private var INSTANCE: AppDB? = null

        fun getAppDB(context: Context): AppDB {
            if (INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDB::class.java, "app-database").allowMainThreadQueries().build()
                }
            }

            return INSTANCE as AppDB
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

    @Dao
    interface listaDAO {

        @get:Query("SELECT * FROM lista")
        val all: List<Lista>

        //@Query("SELECT * FROM Lista")
        //fun allChars(): DataSource.Factory<Int, Lista>
    }
}