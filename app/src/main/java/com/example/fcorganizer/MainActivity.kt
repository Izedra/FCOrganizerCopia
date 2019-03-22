package com.example.fcorganizer

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.fcorganizer.Controladores.Conexiones

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var con = Conexiones()
        con.getPersonaje("", "Ragnarok", this)

    }
}
