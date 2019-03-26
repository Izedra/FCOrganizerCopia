package com.example.fcorganizer.controladores

import android.app.Dialog
import android.os.AsyncTask
import android.util.Log
import com.example.fcorganizer.MainActivity
import com.example.fcorganizer.pojos.PersonajeC
import com.example.fcorganizer.pojos.Resultado
import com.google.gson.Gson
import java.io.FileNotFoundException
import java.net.URL

class GetPersonajeID(private val dialogo: Dialog, private val nombre: String, private val servidor: String): AsyncTask<Any?,Any?,Any?>() {

    private var url: String = "https://xivapi.com/character/search"

    override fun onPreExecute() {
        dialogo.show()
    }

    override fun doInBackground(vararg params: Any?): Any? {

        val listres: List<Resultado?>?
        var charid: Int? = -1

        try {

            //https://xivapi.com/character/search?name=[name]&server=[server]
            val texto = URL("$url?name=${nombre.substringBefore(" ")}+${nombre.substringAfter(" ")}&server=$servidor").readText()
            listres = Gson().fromJson<PersonajeC>(texto, PersonajeC::class.java).Results

            for (i in 0..listres!!.lastIndex) {
                if (listres[i]!!.Name.toString() == nombre) {
                    charid = listres[i]!!.ID!!
                    break
                }
            }

        } catch (ex: FileNotFoundException) {
            Log.d(
                "FileNotFoundException",
                "No se encuentra el personaje $nombre ni/o el servidor $servidor"
            )

            charid = 0
        } catch (ex: NullPointerException) {
            Log.d("NullPointerException", ex.message)
        }

        return charid
    }

    override fun onPostExecute(result: Any?) {
        val getid = MainActivity::getPersonajeId
        getid(MainActivity(), result)
        Log.d("CHARID: ", result.toString())
        dialogo.dismiss()

    }
}