package com.example.fcorganizer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ArrayAdapter
import com.example.fcorganizer.Controladores.Conexiones
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var charid: Int?

        var servers = Conexiones().getServidores()

        var arrAdap: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, servers)
        ac_tv_Servers.threshold = 1
        ac_tv_Servers.dropDownAnchor
        ac_tv_Servers.setAdapter(arrAdap)

        bEntrar.setOnClickListener(){
            var nombre = et_personaje.text.toString()
            charid = Conexiones().getPersonajeID(nombre.substringBefore(" "), nombre.substringAfter(" "), ac_tv_Servers.text.toString())
            if (charid != 0){
                Log.d("ID---------:", charid.toString())
            } else {
                Log.d("ERROR NOMBRE: ", "$nombre no existe")
            }
        }



    }
}
