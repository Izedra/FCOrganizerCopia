package com.example.fcorganizer.Controladores

import android.app.Activity
import android.os.AsyncTask
import android.util.Log
import com.example.fcorganizer.Pojos.PersonajeC
import com.example.fcorganizer.Pojos.PersonajeP
import com.google.gson.Gson
import java.io.FileNotFoundException
import java.io.IOException
import java.lang.NullPointerException
import java.net.URL

class Conexiones(
    private var url: String = "https://xivapi.com/",
    private var key: String = "?key=779c4437a51f43a5bb6dcd34"
){


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

    fun getPersonaje(nombre: String, servidor: String, actividad: Activity){

         try {
             Thread {
                 //https://xivapi.com/character/search?name=[name]&server=[server]
                 var texto = URL("$url/character/search?name=$nombre&server=$servidor$key").readText()

                 actividad.runOnUiThread {
                     var listres = Gson().fromJson<PersonajeC>(texto, PersonajeP::class.java)!!.Results
                     var res1 = listres[0].ID
                 }
             }.start()
         }catch (ex: NullPointerException){
             Log.d("EXCEPTION", ex.cause.toString())
         }catch (ex: FileNotFoundException){
             Log.d("EXCEPTION", ex.cause.toString())
         }

    }
}