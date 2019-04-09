package com.example.fcorganizer

import android.content.Context
import android.net.Uri
import android.os.Bundle
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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
        // INICIALIZA EL RV, HACE LA BUSQUEDA DE LA ID DEL PERSONAJEP Y PROCEDE A RELLENAR EL RV EN EL METODO rellenar RV

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
                res = response.body()!!.Results!![0]!!
                rellenarRV(res!!.ID!!)
            }
        })

    }

    fun rellenarRV(id: Int){

        val callP = cliente.getCharacter(id)

        callP.enqueue(object : Callback<PersonajeP>{

            var lista = ArrayList<Resultado>()
            override fun onFailure(call: Call<PersonajeP>, t: Throwable) {
                dialogo.dismiss()
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(view!!).navigateUp()
            }

            override fun onResponse(call: Call<PersonajeP>, response: Response<PersonajeP>) {
                val respuesta = response.body()
                if (respuesta!!.FreeCompanyMembers != null) {
                    respuesta.FreeCompanyMembers!!.forEach {
                        lista.add(it)
                    }
                }

                if (respuesta.Friends != null) {
                    respuesta.Friends.forEach {
                        lista.add(it)
                    }
                }

                if (!lista.isEmpty()) {
                    rv!!.adapter = AdaptadorCrearLista(lista, context!!, id, listados)
                    dialogo.dismiss()
                } else {
                    dialogo.dismiss()
                    Toast.makeText(context, "El personaje seleccionado no tiene compañeros con los que crear la lista", Toast.LENGTH_LONG).show()
                    Navigation.findNavController(requireView()).navigateUp()
                }
            }
        })

    }

    fun showDialogoNombre(){

        val builder = AlertDialog.Builder(context!!)
        builder.setTitle("Nombre de lista")

        // https://stackoverflow.com/questions/10695103/creating-custom-alertdialog-what-is-the-root-view
        // Seems ok to inflate view with null rootView
        val view = layoutInflater.inflate(R.layout.dialogo_nombre_lista, null)

        val et = view.findViewById(R.id.et_nombre_lista) as EditText

        builder.setView(view)

        // set up the ok button
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

        builder.setNegativeButton(android.R.string.cancel) { dialog, p1 ->
            dialog.cancel()
        }

        builder.show()

    }

    fun guardarLista(lista: Listas, listado: List<Listado>){
        dialogo.show(fragmentManager!!, "")
        val db = BaseDatos(context!!)

        GlobalScope.launch {
            idlista = db.daoLista().insertLista(lista)
            listado.forEach {
                it.idListado = idlista.toInt()
                db.daoListado().insertListado(it)
            }
        }

        dialogo.dismiss()
    }

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
