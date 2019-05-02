package com.example.fcorganizer

import android.content.Context
import android.content.DialogInterface
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    ListasCreadas.OnFragmentInteractionListener,
    FragmentCrearLista.OnFragmentInteractionListener,
    CrearListaRV.OnFragmentInteractionListener,
    VerLista.OnFragmentInteractionListener,
    EditarLista.OnFragmentInteractionListener{

    private var mAuth: FirebaseAuth? = null

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

        mAuth = FirebaseAuth.getInstance()

        // Asigna un menu personalizado a la toolbar
        toolb.inflateMenu(R.menu.toolbar_menu)
        toolb.setOnMenuItemClickListener {onOptionsItemSelected(it)}
    }

    override fun onStart() {
        super.onStart()

        cambiarBotones()
    }

    ///////////// GESTION DE USUARIOS
    fun signUp(email: String, pass: String, dialog: AlertDialog){
        mAuth!!.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful){
                dialog.dismiss()
                cambiarBotones()
            } else {
                Toast.makeText(this, "Fallo en el registro", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun login(email: String, pass: String, dialog: DialogInterface){
        mAuth!!.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful){
                dialog.dismiss()
                Toast.makeText(this, "${mAuth!!.currentUser!!.email}, login correcto", Toast.LENGTH_LONG).show()
                cambiarBotones()
            } else {
                Toast.makeText(this, "Fallo al iniciar sesión", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun logout(){
        mAuth!!.signOut()
        Toast.makeText(this, "Desconectado/a", Toast.LENGTH_LONG).show()
        cambiarBotones()
    }

    fun cambiarBotones(){
        if (getCurrentUser().isNullOrBlank()) {
            findViewById<Toolbar>(R.id.toolbar).menu.findItem(R.id.bm_login).isVisible = true
            findViewById<Toolbar>(R.id.toolbar).menu.findItem(R.id.bm_logout).isVisible = false
            //findViewById<Toolbar>(R.id.toolbar).menu.findItem(R.id.bm_upload).isVisible = false
            //findViewById<Toolbar>(R.id.toolbar).menu.findItem(R.id.bm_download).isVisible = false
        } else {
            findViewById<Toolbar>(R.id.toolbar).menu.findItem(R.id.bm_logout).isVisible = true
            findViewById<Toolbar>(R.id.toolbar).menu.findItem(R.id.bm_login).isVisible = false
            //findViewById<Toolbar>(R.id.toolbar).menu.findItem(R.id.bm_upload).isVisible = true
            //findViewById<Toolbar>(R.id.toolbar).menu.findItem(R.id.bm_download).isVisible = true
        }
    }

    fun getCurrentUser(): String? {
        return try {
            Log.i("USUARIO ACTUAL: ", mAuth!!.currentUser!!.email.toString())
            mAuth!!.currentUser!!.email.toString()
        }catch (ex:KotlinNullPointerException){
            null
        }
    }

    private fun signdialog(dialogo: Boolean){

        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)

        val view = layoutInflater.inflate(R.layout.dialogo_sign, null)

        val etEmail = view.findViewById<EditText>(R.id.et_email)
        val etPass = view.findViewById<EditText>(R.id.et_pass)

        etEmail.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                etEmail.error = null
            }

        })

        builder.setView(view)

        if (dialogo) {
            builder.setTitle("Registrar nuevo usuario")
            builder.setPositiveButton("Registrar", null)
            builder.setNegativeButton("Cancelar", null)
            builder.setNeutralButton("Ya tengo cuenta", null)

            val dig = builder.show()

            dig.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener{
                when {
                    etEmail.text.isBlank() -> etEmail.error = "Campo necesario"
                    etPass.text.isBlank() -> etPass.error = "Campo necesario"
                    else -> signUp(etEmail.text.toString(), etPass.text.toString(), dig)
                }
            }

            dig.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener {
                dig.dismiss()
                signdialog(false)
            }
        }
        else {
            builder.setTitle("Iniciar sesión")
            builder.setPositiveButton("Entrar", null)
            builder.setNegativeButton("Cancelar", null)

            val dig = builder.show()

            dig.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                when {
                    etEmail.text.isBlank() -> etEmail.error = "Campo necesario"
                    etPass.text.isBlank() -> etPass.error = "Campo necesario"
                    else -> login(etEmail.text.toString(), etPass.text.toString(), dig)
                }
            }
        }
    }
    //////////// FIN DE GESTION DE USUARIOS

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
                " * En el momento de editar una lista, una vez accedamos a la interfaz pertinente, simplemente tendremos que dejar seleccionados los personajes que queramos que se queden en la lista, de esta manera, podremos crear listas vacias para un propósito concreto y, más tarde, ampliar y/o reducir su tamaño como lo estimemos oportuno\n" +
                "\n" +
                " ----\n" +
                "\n" +
                " Por ahora el logueo solo sirve para guardar las listas en Firebase, independientemente del correo que se use, no las guarda si se está desconectado de Firebase")

        builder.setPositiveButton("OK"){dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

    fun upload(){

    }

    fun download(){

    }

    // Controla los botones del menu
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val navController = nav_host_fragment.findNavController()
        when (item!!.itemId){
            R.id.bm_info -> mostrarInfo()
            R.id.bm_login -> signdialog(true)
            R.id.bm_logout -> logout()
            //R.id.bm_download -> download()
            //R.id.bm_upload -> upload()
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
