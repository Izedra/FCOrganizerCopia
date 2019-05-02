package com.example.fcorganizer

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fcorganizer.conexiones.ClaseRetrofit
import com.example.fcorganizer.conexiones.RetrofitGenerator
import com.example.fcorganizer.adaptadores.AdaptadorCrearLista
import com.example.fcorganizer.database.BaseDatos
import com.example.fcorganizer.pojos.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.IndexOutOfBoundsException


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CrearListaRV.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CrearListaRV.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class CrearListaRV : Fragment(){

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private val dialogo = ProgresoFragment()
    private var idlista: Long = -1
    private var rv: RecyclerView? = null
    private val cliente = RetrofitGenerator.crearObjeto(ClaseRetrofit::class.java)
    private val listados: ArrayList<Listado> = ArrayList()
    private var res: Resultado? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_crear_lista_rv, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // INICIALIZA EL RV, HACE LA BUSQUEDA DE LA ID DEL PERSONAJEP Y PROCEDE A RELLENAR EL RV EN EL METODO rellenarRV

        dialogo.show(fragmentManager!!, "")
        rv = view.findViewById(R.id.rv_personajes)
        rv!!.layoutManager = LinearLayoutManager(context)

        val callC = cliente.getCharId(CrearListaRVArgs.fromBundle(arguments!!).charname, CrearListaRVArgs.fromBundle(arguments!!).charserver)
        callC.enqueue(object : Callback<PersonajeC>{
            override fun onFailure(call: Call<PersonajeC>, t: Throwable) {
                dialogo.dismiss()
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(requireView()).navigateUp()
            }

            override fun onResponse(call: Call<PersonajeC>, response: Response<PersonajeC>) {
                try {
                    res = response.body()!!.Results!![0]!!
                    rellenarRV(res!!.ID!!)
                }catch (ex: IndexOutOfBoundsException){
                    dialogo.dismiss()
                    Toast.makeText(context, "El personaje introducido no existe", Toast.LENGTH_LONG).show()
                    Navigation.findNavController(requireView()).navigateUp()
                }
            }
        })
    }

    // Recibe un id mediante el cuál hará una consulta a la API y devolverá la lista de personajes asociados al id
    fun rellenarRV(id: Int){

        val callP = cliente.getCharacter(id)

        callP.enqueue(object : Callback<PersonajeP>{

            var lista = ArrayList<Resultado>()

            // Que hacer si la conexión da algún tipo de error
            override fun onFailure(call: Call<PersonajeP>, t: Throwable) {
                dialogo.dismiss()
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(view!!).navigateUp()
            }

             override fun onResponse(call: Call<PersonajeP>, response: Response<PersonajeP>) {
                val respuesta = response.body()
                if (respuesta!!.FreeCompanyMembers != null) {
                    respuesta.FreeCompanyMembers!!.forEach {
                        if (it.ID != id) {
                            lista.add(it)
                        }
                    }
                }

                // Por cada iteración de la lista de amigos, comprueba que no se haya añadido ya un elemento a la lista
                // con el mismo nombre y servidor (el mismo personaje) y si no es asi lo añade
                if (respuesta.Friends != null) {
                    respuesta.Friends.forEach {
                        val cadena = it.Name+it.Server
                        var addit = true
                        for (i in 1..lista.size){
                            try {
                                if (lista[i].Name + lista[i].Server != cadena) {
                                    addit = true
                                } else {
                                    addit = false
                                    break
                                }
                            }catch (ex: IndexOutOfBoundsException){}
                        }

                        if (addit) {
                            lista.add(it)
                        }
                    }
                }

                // Comprueba si la lista creada está vacia...
                if (!lista.isEmpty()) {
                    rv!!.adapter = AdaptadorCrearLista(lista, context!!, id, listados) // Si no lo está, crea la interfaz...
                    dialogo.dismiss()
                } else { // Si lo está, simplemente vuelve a la selección de personaje tras burlarse del personaje introducido
                    dialogo.dismiss()
                    Toast.makeText(context, "El personaje seleccionado está más solo que la una (o partes de su perfil son privadas, vaya creíd@ (¬_¬))", Toast.LENGTH_LONG).show()
                    Navigation.findNavController(requireView()).navigateUp()
                }
            }
        })
    }

    // Lanza un dialogo en el que introducir un nombre para la lista y llama a la función [guardarLista]
    fun showDialogoNombre(){

        val builder = AlertDialog.Builder(context!!)
        builder.setTitle("Nombre de lista")

        val view = layoutInflater.inflate(R.layout.dialogo_nombre_lista, null)

        val et = view.findViewById(R.id.et_nombre_lista) as EditText

        builder.setView(view)

        // Asigna funcion a los botones 'Aceptar' y 'Cancelar'
        builder.setPositiveButton(android.R.string.ok) { dialog, p1 ->
            val newCategory = et.text
            var isValid = true
            if (newCategory.isBlank()) {
                Toast.makeText(context, "Introduzca un nombre", Toast.LENGTH_SHORT).show()
                isValid = false
            }

            if (isValid) {
                guardarLista(Listas(0, et.text.toString(), res!!.Name!!, res!!.Server!!, res!!.Avatar.toString()), listados)
                dialog.dismiss()
                llamadaMain()
            }
        }

        builder.setNegativeButton(android.R.string.cancel) { dialog, _ ->
            dialog.cancel()
        }

        builder.setCancelable(false)
        builder.show()

    }

    // Guarda la lista creada en la base de datos
    fun guardarLista(lista: Listas, listado: List<Listado>){
        dialogo.show(fragmentManager!!, "")
        val db = BaseDatos(context!!)

        val auth = MainActivity().getCurrentUser()

        GlobalScope.launch {
            idlista = db.daoLista().insertLista(lista)
            var listasref: DatabaseReference? = null
            if (auth != null) {
                listasref = FirebaseDatabase.getInstance().reference.child(idlista.toString())
                lista.idLista = idlista.toInt()
                listasref.setValue(lista)
            }
            listado.forEach {
                it.idListado = idlista.toInt()
                db.daoListado().insertListado(it)
                if (auth != null) {
                    listasref!!.child(it.nombre + it.servidor).setValue(it)
                }

            }
        }

        dialogo.dismiss()
    }

    /*fun writeFirebase(){

        val rootref = FirebaseDatabase.getInstance().reference
        val listaref = rootref.child("prueba")
        val fireref = listaref.child("lista1")
        fireref.setValue(Listas(56,"identificador2","nombre2", "servidor1","avatar1"))
    }*/

    // Navega al fragment principal
    fun llamadaMain(){
        Navigation.findNavController(view!!).navigate(CrearListaRVDirections.actionCrearListaRVToListasCreadas())
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
            val botonGuardar = activity!!.findViewById<Toolbar>(R.id.toolbar).menu.findItem(R.id.bm_guardar)
            botonGuardar.isVisible = true
            botonGuardar.setOnMenuItemClickListener {
                showDialogoNombre()
                true
            }
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        listados.forEach{
            println(it.nombre)
        }
        activity!!.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar).menu.findItem(R.id.bm_guardar).isVisible = false

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CrearListaRV.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CrearListaRV().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
