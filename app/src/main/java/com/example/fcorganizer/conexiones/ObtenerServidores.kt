package com.example.fcorganizer.conexiones

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileNotFoundException
import java.net.URL

class ObtenerServidores(): Thread() {

    private var texto: String = ""

    fun getServidores (): List<String>? {

        var lista: List<String> = emptyList()
        var tipo = object : TypeToken<List<String>>() {}.type

        var t = Thread {
            try {
                //https://xivapi.com/servers
                texto = URL("https://xivapi.com/servers").readText()

                lista = Gson().fromJson(texto, tipo)

            } catch (ex: FileNotFoundException) {
                Log.d("FileNotFoundException", "No se puede acceder a la lista de servidores")
            } catch (ex: NullPointerException) {
                Log.d("NullPointerException", ex.message)
            }
        }

        t.start()

        t.join()

        return lista
    }
}