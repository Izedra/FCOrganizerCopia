package com.example.fcorganizer.controladores

import android.app.Dialog
import android.os.AsyncTask
import android.util.Log
import com.example.fcorganizer.MainActivity
import com.example.fcorganizer.pojos.PersonajeC
import com.example.fcorganizer.pojos.Resultado

class GetListaPersonajes(private val dialogo: Dialog, private val nombre: String, private val servidor: String): AsyncTask<Any, Any, Any>() {

    override fun onPreExecute() {
        dialogo.show()
    }

    override fun doInBackground(vararg params: Any?): Any? {
        var lista: ArrayList<Resultado> = ArrayList()








        val getid = MainActivity::getPersonajes
        getid(MainActivity(), lista)

        return lista
    }

    override fun onPostExecute(result: Any?) {
        dialogo.dismiss()
    }
}