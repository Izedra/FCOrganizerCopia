package com.example.fcorganizer.conexiones

import com.example.fcorganizer.pojos.PersonajeC
import com.example.fcorganizer.pojos.PersonajeP
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ClaseRetrofit {

    @GET("character/search")
    fun getCharId(
        @Query("name") name: String,
        @Query("server") server: String
    ): Call<PersonajeC>

    @GET("character/{id}?data=FR,FCM")
    fun getCharacter(
        @Path("id") id: Int
    ): Call<PersonajeP>
}