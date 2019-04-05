package com.example.fcorganizer.conexiones

import android.content.SharedPreferences
import android.os.AsyncTask
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import com.example.fcorganizer.pojos.PersonajeC
import com.example.fcorganizer.pojos.PersonajeP
import com.example.fcorganizer.pojos.Resultado
import com.google.gson.Gson
import java.lang.IndexOutOfBoundsException
import java.net.URL

class AsyncGet(
    private val dialogo: DialogFragment,
    private val prefs: SharedPreferences,
    private val findNavController: NavController,
    private val toast: Toast,
    private val personaje: String,
    private val server: String
    ): AsyncTask<Void, Void, ArrayList<Resultado>>() {


    override fun onPostExecute(result: ArrayList<Resultado>) {
        super.onPostExecute(result)

        if (result.count() != 0) {
            println(result.count().toString())
            dialogo.dismiss()
            //indNavController.navigate(FragmentCrearListaDirections.actionFragmentCrearListaToCrearListaRV())
        } else {
            toast.show()
        }

        dialogo.dismiss()
    }

    override fun doInBackground(vararg p0: Void?): ArrayList<Resultado> {

        val texto = URL("https://xivapi.com/character/search?name=${personaje.substringBefore(" ").trim()}+${personaje.substringAfter(" ").trim()}&server=$server").readText()

        val resultados = Gson().fromJson(texto, PersonajeC::class.java).Results
        try {
            val texto2 = URL("https://xivapi.com/character/${resultados?.get(0)?.ID}?data=FR,FCM").readText()

            val personaje = Gson().fromJson(texto2, PersonajeP::class.java)

            val lista = ArrayList<Resultado>()

            if (personaje.FreeCompanyMembers != null) {
                for (i in 0..personaje.FreeCompanyMembers.lastIndex) {
                    lista.add(personaje.FreeCompanyMembers[i])
                }
            }

            if (personaje.Friends != null) {
                for (i in 0..personaje.Friends.lastIndex) {
                    lista.add(personaje.Friends[i])
                }
            }
            return lista
        }catch (ex: IndexOutOfBoundsException){
            return ArrayList()
        }
    }
}