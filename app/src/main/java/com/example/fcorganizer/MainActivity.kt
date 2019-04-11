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

    // Controla los botones del menu
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val navController = nav_host_fragment.findNavController()
        return item!!.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
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
