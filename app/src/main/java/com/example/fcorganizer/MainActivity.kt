package com.example.fcorganizer

import android.content.Context
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    ListasCreadas.OnFragmentInteractionListener,
    FragmentCrearLista.OnFragmentInteractionListener,
    CrearListaRV.OnFragmentInteractionListener,
    VerLista.OnFragmentInteractionListener,
    EditarLista.OnFragmentInteractionListener{

    override fun onFragmentInteraction(uri: Uri) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa la toolbar inteligente de Navigation
        val navController = findNavController(R.id.nav_host_fragment)
        navController.setGraph(R.navigation.nav_graph)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        val toolb = findViewById<Toolbar>(R.id.toolbar)
        toolb.setupWithNavController(navController, appBarConfiguration)

        // Asigna un menu personalizado a la toolbar
        toolb.inflateMenu(R.menu.toolbar_menu)
        toolb.setOnMenuItemClickListener {onOptionsItemSelected(it)}
    }

    // Infla el menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    fun mostrarInfo(){
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Funcionamiento")

        builder.setMessage("\n" +
                "\n" +
                " * Introduciendo un personaje válido, permite acceder al círculo de contactos de este mediante XIVApi\n" +
                "\n" +
                " * Una vez obtenido, se creará una lista de la cuál se pueden seleccionar tantos personajes como se quiera\n" +
                "\n" +
                " * Cuando se haya decidido el tamaño de la lista, podemos guardarla pulsando el botón pertinente\n" +
                "\n" +
                " * En el diálogo que aparecerá, se introducirá un nombre identificativo para la lista (puede repetirse)\n" +
                "\n" +
                " * Una vez creada la lista, desde la pantalla principal, podremos tanto acceder a ella para su visualización completa, como borrarla o editarla\n" +
                "\n" +
                " * Al intentar borrar una lista, un diálogo nos pedirá confirmación previa (esta acción no es reversible)\n" +
                "\n" +
                " * En el momento de editar una lista, una vez accedamos a la interfaz pertinente, simplemente tendremos que dejar seleccionados los personajes que queramos que se queden en la lista, de esta manera, podremos crear listas vacias para un propósito concreto y, más tarde, ampliar y/o reducir su tamaño como lo estimemos oportuno\n")

        builder.setPositiveButton("OK"){dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

    // Controla los botones del menu
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val navController = nav_host_fragment.findNavController()
        when (item!!.itemId){
             R.id.bm_info -> mostrarInfo()
        }
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    // Manda la aplicación al fondo o retrocede de Fragment al pulsar el botón de retroceso de android
    override fun onBackPressed() {
        when(NavHostFragment.findNavController(nav_host_fragment).navigateUp()){
            false -> moveTaskToBack(true)
        }
    }

    // Retira el teclado al pulsar fuera de los campos de texto
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev!!.action == MotionEvent.ACTION_DOWN){
            val v = currentFocus
            if (v is EditText){
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())){
                    v.clearFocus()
                    val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }

        return super.dispatchTouchEvent(ev)
    }
}
