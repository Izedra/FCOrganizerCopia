package com.example.fcorganizer.conexiones

import android.content.SharedPreferences
import android.os.AsyncTask
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import com.example.fcorganizer.ListasCreadas
import com.example.fcorganizer.ListasCreadasDirections
import com.example.fcorganizer.pojos.PersonajeC
import com.example.fcorganizer.pojos.PersonajeP
import com.example.fcorganizer.pojos.Resultado
import com.google.gson.Gson
import java.net.URL

class AsyncGetPersonajes(
    private val dialogo: DialogFragment,
    private val prefs: SharedPreferences,
    private val findNavController: NavController
): AsyncTask<Void, Void, String>() {


    override fun onPostExecute(result: String) {
        super.onPostExecute(result)
        prefs.edit().putString("lista", result).apply()
        dialogo.dismiss()
        findNavController.navigate(ListasCreadasDirections.actionListasCreadasToVerLista())
    }

    override fun doInBackground(vararg p0: Void?): String? {
        val texto = URL("https://xivapi.com/character/search?name=Izedra+Argent&server=Ragnarok").readText()

        val resultados = Gson().fromJson(texto, PersonajeC::class.java).Results

        println(resultados!![0]!!.ID)

        val texto2 = URL("https://xivapi.com/character/${resultados[0]?.ID}?data=FR,FCM").readText()

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

        return Gson().toJson(lista)
    }
}