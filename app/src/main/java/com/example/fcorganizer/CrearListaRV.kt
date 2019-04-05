package com.example.fcorganizer

import android.app.Application
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.room.Room
import com.example.fcorganizer.database.BaseDatos
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_crear_lista_rv.*


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
    private var nombre: String = ""
    private var server: String = ""

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

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.bm_borrar).isVisible = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //cargarRV()

        dialogo.show(fragmentManager!!, "")
//        if (!dialogo.isVisible){
//
//        } else {
        println(CrearListaRVArgs.fromBundle(arguments!!).charname + " ----------------------- " + CrearListaRVArgs.fromBundle(arguments!!).charserver)
        dialogo.dismiss()
        Toast.makeText(context, "El personaje introducido no conoce a nadie...", Toast.LENGTH_SHORT).show()
        //Navigation.findNavController(view).navigateUp()

        val db = BaseDatos(requireContext())

    }

    fun cargarRV(){
        dialogo.show(fragmentManager!!, "")
//        if (!dialogo.isVisible){
//
//        } else {
        println(CrearListaRVArgs.fromBundle(arguments!!).charname + " ----------------------- " + CrearListaRVArgs.fromBundle(arguments!!).charserver)
        dialogo.dismiss()
        Toast.makeText(context, "El personaje introducido no conoce a nadie...", Toast.LENGTH_SHORT).show()
        Navigation.findNavController(view!!).navigateUp()
//        }
//        dialogo.dismiss()
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
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        activity!!.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar).menu.findItem(R.id.bm_borrar).isVisible = false

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
