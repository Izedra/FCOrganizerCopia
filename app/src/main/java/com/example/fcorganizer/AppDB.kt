package com.example.fcorganizer

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fcorganizer.pojos.Lista
import com.example.fcorganizer.pojos.Resultado
import kotlin.reflect.KClass

@Database(entities = [(Lista::class)], version = 1)
abstract class AppDB: RoomDatabase() {
}