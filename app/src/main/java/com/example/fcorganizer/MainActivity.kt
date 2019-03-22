package com.example.fcorganizer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.fcorganizer.Controladores.Conexiones

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var con = Conexiones()
        var charid = con.getPersonajeID("Izedra", "Argent","Ragnarok", this)
        Log.d("ID----------", charid.toString())
    }
}
