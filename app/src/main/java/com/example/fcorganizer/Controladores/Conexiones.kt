package com.example.fcorganizer.Controladores

import android.app.Activity
import android.util.Log
import com.example.fcorganizer.Pojos.PersonajeC
import com.example.fcorganizer.Pojos.Resultado
import com.google.gson.Gson
import java.io.FileNotFoundException
import java.net.URL

class Conexiones: Thread() {
    private var url: String = "https://xivapi.com"
    private var key: String = "key=974c583ec3794327ba1ffa16"



    /*

     Ejemplo de hilo tras la UI

     Thread {
            // Obtención del código HTML a mostrar
            var start = System.currentTimeMillis()
            texto = URL("https://www.latiendadirecta.es/api/v1/page").readText()

            // Formateo del mismo al mismo tiempo que se introduce en el textView
            activity!!.runOnUiThread {
                view2.tvQuienesSomos.text = Html.fromHtml(Gson().fromJson<Page>(texto, Page::class.java)!!.view)
                var finish = System.currentTimeMillis()

                // Muestra el tiempo de respuesta desde el comienzo del hilo hasta la escritura del textView
                //Log.d("TIEMPO",""+(finish-start))


                // Mostramos el textView terminado
                view2.tvQuienesSomos.visibility = View.VISIBLE
            }
        }.start()

    */

    fun getPersonajeID (nombre: String, apellido: String, servidor: String, actividad: Activity): Int? {

        var texto: String
        var listres: List<Resultado?>?
        var charid: Int? = 0

        var t = Thread {
            try {
                //https://xivapi.com/character/search?name=[name]&server=[server]
                texto = URL("$url/character/search?name=$nombre+$apellido&server=$servidor&$key").readText()
                listres = Gson().fromJson<PersonajeC>(texto, PersonajeC::class.java).Results

                for (i in 0..listres!!.lastIndex) {
                    if (listres!![i]!!.Name.toString() == "$nombre $apellido") {
                        charid = listres!![i]!!.ID!!
                        break
                    }
                }


            } catch (ex: FileNotFoundException) {
                Log.d("FileNotFoundException", "No se encuentra el personaje $nombre ni/o el servidor $servidor")
            } catch (ex: NullPointerException) {
                Log.d("NullPointerException", ex.message)
            }
        }
        t.start()

        t.join()

//        Log.d("ID--------------", charid.toString())
        return charid
    }
}