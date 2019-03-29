package com.example.fcorganizer

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(),
    ListasCreadas.OnFragmentInteractionListener,
    FragmentCrearLista.OnFragmentInteractionListener,
    CrearListaRV.OnFragmentInteractionListener,
    VerLista.OnFragmentInteractionListener {

    lateinit var progressDialog: AlertDialog

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val progdialBuilder = AlertDialog.Builder(applicationContext)
        progdialBuilder.setView(R.layout.progress_dialog)
        progressDialog = progdialBuilder.create()
    }




}
