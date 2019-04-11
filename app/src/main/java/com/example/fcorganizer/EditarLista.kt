package com.example.fcorganizer

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.fcorganizer.adaptadores.AdaptadorEditarLista
import com.example.fcorganizer.conexiones.ClaseRetrofit
import com.example.fcorganizer.conexiones.RetrofitGenerator
import com.example.fcorganizer.database.BaseDatos
import com.example.fcorganizer.pojos.Listado
import com.example.fcorganizer.pojos.PersonajeC
import com.example.fcorganizer.pojos.PersonajeP
import com.example.fcorganizer.pojos.Resultado
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
 * [EditarLista.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [EditarLista.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class EditarLista : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private val dialogo = ProgresoFragment()
    private val cliente = RetrofitGenerator.crearObjeto(ClaseRetrofit::class.java)
    private var rv: RecyclerView? = null
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
        return inflater.inflate(R.layout.fragment_editar_lista, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val callC = cliente.getCharId(EditarListaArgs.fromBundle(arguments!!).nombre, EditarListaArgs.fromBundle(arguments!!).servidor)
        callC.enqueue(object : Callback<PersonajeC> {
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

    fun rellenarRV(id: Int){

        val callP = cliente.getCharacter(id)

        callP.enqueue(object : Callback<PersonajeP>{

            var lista = ArrayList<Listado>()
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
                            lista.add(Listado(arguments!!.getInt("idLista"), it.Name!!, it.Server!!, it.Avatar.toString()))
                        }
                    }
                }

                // Por cada it de la lista de amigos, comprueba que no se haya añadido ya un elemento a la lista
                // con el mismo nombre y servidor (el mismo personaje) y si no es asi lo añade
                if (respuesta.Friends != null) {
                    respuesta.Friends.forEach {
                        val cadena = it.Name+it.Server
                        var addit = true
                        for (i in 1..lista.size){
                            try {
                                if (lista[i].nombre + lista[i].servidor != cadena) {
                                    addit = true
                                } else {
                                    addit = false
                                    break
                                }
                            }catch (ex: IndexOutOfBoundsException){}
                        }

                        if (addit) {
                            lista.add(Listado(arguments!!.getInt("idLista"), it.Name!!, it.Server!!, it.Avatar.toString()))
                        }
                    }
                }

                // Comprueba si la lista creada está vacia...
                if (!lista.isEmpty()) {// Si no lo está, crea la interfaz...
                    var listaexistente: ArrayList<Listado>
                    GlobalScope.launch {
                        listaexistente = BaseDatos(context!!).daoListado().getListado(EditarListaArgs.fromBundle(arguments!!).idLista) as ArrayList<Listado>
                        rv!!.adapter = AdaptadorEditarLista(lista, context!!, listaexistente)
                    }

                    dialogo.dismiss()
                } else { // Si lo está, simplemente vuelve a la selección de personaje tras burlarse del personaje introducido
                    dialogo.dismiss()
                    Toast.makeText(context, "El personaje seleccionado está más solo que la una (o partes de su perfil son privadas, vaya creíd@ (¬_¬))", Toast.LENGTH_LONG).show()
                    Navigation.findNavController(requireView()).navigateUp()
                }
            }
        })
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
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
         * @return A new instance of fragment EditarLista.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditarLista().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
