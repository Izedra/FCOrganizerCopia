package com.example.fcorganizer

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fcorganizer.conexiones.ClaseRetrofit
import com.example.fcorganizer.conexiones.RetrofitGenerator
import com.example.fcorganizer.database.BaseDatos
import com.example.fcorganizer.pojos.*
import kotlinx.android.synthetic.main.activity_main.*
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
class CrearListaRV : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private val dialogo = ProgresoFragment()
    private val ID_FRAGMENTO: Int = 3
    private var idlista: Long = -1
    private var rv: RecyclerView? = null
    private val cliente = RetrofitGenerator.crearObjeto(ClaseRetrofit::class.java)

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
        //cargarRV()

        dialogo.show(fragmentManager!!, "")
        rv = view.findViewById(R.id.rv_personajes)
        rv!!.layoutManager = LinearLayoutManager(context)


//        if (!dialogo.isVisible){
//
//        } else {
//        println(CrearListaRVArgs.fromBundle(arguments!!).charname + " ----------------------- " + CrearListaRVArgs.fromBundle(arguments!!).charserver)
//        dialogo.dismiss()
//        Toast.makeText(context, "El personaje introducido no conoce a nadie...", Toast.LENGTH_SHORT).show()
//        //Navigation.findNavController(view).navigateUp()
//
//        val db = BaseDatos(requireContext())
//
//
//        GlobalScope.launch {
//            db.daoLista().insertLista(Listas(0, "Lista1",nombre,server,"https://img2.finalfantasyxiv.com/f/db228dcf48f9f1f23890da6926a5d6cc_40d57ba713628f3f1ef5ef204b6d76d2fc0_96x96.jpg"))
//            var data = db.daoLista().getListas()
//
//            data.forEach {
//                println("----------------------------------------$it-------------------------")
//            }
//        }

        val callC = cliente.getCharId(CrearListaRVArgs.fromBundle(arguments!!).charname, CrearListaRVArgs.fromBundle(arguments!!).charserver)
        callC.enqueue(object : Callback<PersonajeC>{
            override fun onFailure(call: Call<PersonajeC>, t: Throwable) {
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(requireView()).navigateUp()
            }

            override fun onResponse(call: Call<PersonajeC>, response: Response<PersonajeC>) {
                val respuesta = response.body()
                rellenarRV(respuesta!!.Results!![0]!!.ID!!)
            }
        })

    }

    fun rellenarRV(id: Int){

        val callP = cliente.getCharacter(id)

        callP.enqueue(object : Callback<PersonajeP>{

            var lista = ArrayList<Resultado>()
            override fun onFailure(call: Call<PersonajeP>, t: Throwable) {
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show()
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
                    rv!!.adapter = AdaptadorCrearLista(lista, context!!)
                    dialogo.dismiss()
                } else {
                    dialogo.dismiss()
                    Toast.makeText(context, "El personaje seleccionado no tiene compañeros con los que crear la lista", Toast.LENGTH_LONG).show()
                    Navigation.findNavController(requireView()).navigateUp()
                }
            }
        })

    }

//    fun cargarRV(){
//        dialogo.show(fragmentManager!!, "")
////        if (!dialogo.isVisible){
////
////        } else {
//        println(CrearListaRVArgs.fromBundle(arguments!!).charname + " ----------------------- " + CrearListaRVArgs.fromBundle(arguments!!).charserver)
//        dialogo.dismiss()
//        Toast.makeText(context, "El personaje introducido no conoce a nadie...", Toast.LENGTH_SHORT).show()
//        Navigation.findNavController(view!!).navigateUp()
////        }
////        dialogo.dismiss()
//    }

    fun guardarLista(lista: Listas, listado: List<Listado>){
        dialogo.show(fragmentManager!!, "")
        val db = BaseDatos(requireContext())

        GlobalScope.launch {
            idlista = db.daoLista().insertLista(lista)
            listado.forEach {
                it.idListado = idlista.toInt()
                db.daoListado().insertListado(it)
            }
        }

        llamadaMain()
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
            activity!!.findViewById<Toolbar>(R.id.toolbar).menu.findItem(R.id.bm_guardar).isVisible = true
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
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
