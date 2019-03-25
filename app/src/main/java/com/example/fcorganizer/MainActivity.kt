package com.example.fcorganizer

import android.app.AlertDialog
import android.app.Dialog
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.widget.ArrayAdapter
import com.example.fcorganizer.Controladores.Conexiones
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // VARIABLES GLOBALES
    private var charid: Int? = null

    // CREACIÓN DE UN DIÁLOGO A MOSTRAR CUANDO LA APLICACIÓN SE ENCUENTRE CARGANDO DATOS



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // CREACION DEL DIALOGO 'CARGANDO'
        val dialogo = Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog)
        dialogo.setContentView(R.layout.progress_dialog)

        val conexion = Conexiones()

        // INTRODUCCION DE LOS SERVIDORES EXISTENTES A TRAVES DE UN ADAPTADOR EN UN CAMPO AUTOCOMPLETADO
        var servers = conexion.getServidores()
        var arrAdap: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, servers)
        ac_tv_Servers.threshold = 1
        ac_tv_Servers.dropDownAnchor
        ac_tv_Servers.setAdapter(arrAdap)

        // ACCION QUE LLEVA A CABO EL BOTON PARA CREAR UNA NUEVA LISTA A PARTIR DE UN PERSONAJE Y UN SERVIDOR
        bEntrar.setOnClickListener(){
            dialogo.show()
            if (ac_tv_Servers.text.count() != 0) {
                var nombre = et_personaje.text.toString()

                charid = conexion.getPersonajeID(nombre.substringBefore(" "), nombre.substringAfter(" "), ac_tv_Servers.text.toString())
                if (charid != 0) {
                    Log.d("ID---------:", charid.toString())
                } else {
                    Log.d("ERROR NOMBRE: ", "$nombre no existe")
                }
            }
            dialogo.dismiss()
        }

        bCerrar.setOnClickListener(){}
    }

    // CREACIÓN DE LA BARRA DE ACCIÓN DE LA APLICACIÓN
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.principal_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

}
