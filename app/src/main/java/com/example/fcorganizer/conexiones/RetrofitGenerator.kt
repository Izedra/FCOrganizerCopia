package com.example.fcorganizer.conexiones

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitGenerator {
    val BASE_URL = "https://xivapi.com/"

    val builder = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())

    var retrofit = builder.build()

    val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    val clientehttp = OkHttpClient.Builder()

    fun <S> crearObjeto(serviceClass: Class<S>): S{
        if (!clientehttp.interceptors().contains(logging)) {
            clientehttp.addInterceptor(logging)
            builder.client(clientehttp.build())
            retrofit = builder.build()
        }
        return retrofit.create(serviceClass)
    }
}