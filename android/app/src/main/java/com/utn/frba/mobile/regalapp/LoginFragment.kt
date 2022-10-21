package com.utn.frba.mobile.regalapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ar.edu.utn.frba.mobile.clases.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()

            /*
            // OBLIGAR A QUE USUARIO PONGA AMBOS DATOS
            if( username.isEmpty() || password.isEmpty()){ // ALGUN DATO VACIO
                val toast = Toast.makeText(activity,"Por favor completar todos los datos", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.BOTTOM,0,0)
                toast.show()
            }else{
                // ACA REVISARIA SI USUARIO EXISTE EN LA BD
                listener!!.onLogin(username, password)
            }
         */

            listener!!.onLogin(username, password)
            // FIXME: CODEAR CODIGO EN MainActivity.kt
        }

        binding.registerButton.setOnClickListener {
            listener!!.onSignUp()
            // FIXME: CODEAR CODIGO EN MainActivity.kt
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onLogin(username: String, password: String)
        fun onSignUp()
    }
}