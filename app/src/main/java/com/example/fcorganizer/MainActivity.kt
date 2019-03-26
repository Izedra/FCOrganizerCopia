package com.example.fcorganizer

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.example.fcorganizer.controladores.GetListaPersonajes
import com.example.fcorganizer.controladores.ObtenerServidores
import com.example.fcorganizer.controladores.GetPersonajeID
import com.example.fcorganizer.pojos.PersonajeC
import com.example.fcorganizer.pojos.PersonajeP
import com.example.fcorganizer.pojos.Resultado
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // VARIABLES GLOBALES
    private var charid: Int? = null
    private lateinit var infodialog: Dialog

    // CREACIÓN DE UN DIÁLOGO A MOSTRAR CUANDO LA APLICACIÓN SE ENCUENTRE CARGANDO DATOS



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // CREACIÓN DEL DIÁLOGO 'INFORMACIÓN'
        infodialog = Dialog(this, android.R.style.Theme_DeviceDefault_Dialog)
        infodialog.setTitle("Información")
        infodialog.setContentView(R.layout.info_dialog)
        infodialog.setCancelable(true)


        // CREACION DEL DIALOGO 'CARGANDO'
        val dialogo = Dialog(this, R.style.CustomTheme)
        dialogo.setContentView(R.layout.progress_dialog)
        dialogo.setCancelable(false)

        // INTRODUCCION DE LOS SERVIDORES EXISTENTES A TRAVES DE UN ADAPTADOR EN UN CAMPO AUTOCOMPLETADO
        var servers = ObtenerServidores().getServidores()
        var arrAdap: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, servers)
        ac_tv_Servers.threshold = 0
        ac_tv_Servers.dropDownAnchor
        ac_tv_Servers.setAdapter(arrAdap)

        // ACCION QUE LLEVA A CABO EL BOTON PARA CREAR UNA NUEVA LISTA A PARTIR DE UN PERSONAJE Y UN SERVIDOR
        bEntrar.setOnClickListener() {
            var nombre = et_personaje.text.toString()
            var servidor = ""
            if (ac_tv_Servers.text.trim().count() > 0){
                servidor = ac_tv_Servers.text.trim().toString()
            }
            GetListaPersonajes(dialogo, nombre, servidor).execute()
        }
    }

    // CREACIÓN DE LA BARRA DE ACCIÓN DE LA APLICACIÓN
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.principal_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // CONTROL DE LOS BOTONES DE LA BARRA DE ACCIÓN
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.binfoapp -> infodialog.show()
        }

        return super.onOptionsItemSelected(item)
    }

    // OBTENCIÓN DEN UNA LISTA DE PERSONAJES PARA RELLENAR EL RECYCLER VIEW
    fun getPersonajes(personajes: List<Resultado>){

    }

}
