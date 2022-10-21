package com.utn.frba.mobile.regalapp


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.utn.frba.mobile.regalapp.R
import com.utn.frba.mobile.regalapp.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpButton.setOnClickListener {
            listener!!.onFinishSignUp()
            // FIXME: CODEAR CODIGO EN MainActivity.kt
        }

        viewModel = ViewModelProvider(activity!!).get(SignUpViewModel::class.java)

        binding.firstname.setText(viewModel.firstname)
        binding.lastname.setText(viewModel.lastname)
        binding.username.setText(viewModel.username)
        binding.password.setText(viewModel.password)
        binding.passwordAgain.setText(viewModel.passwordAgain)
    }

    override fun onPause() {
        viewModel.firstname = binding.firstname.text.toString()
        viewModel.lastname = binding.lastname.text.toString()
        viewModel.username = binding.username.text.toString()
        viewModel.password = binding.password.text.toString()
        viewModel.passwordAgain = binding.passwordAgain.text.toString()

        super.onPause()
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
        fun onFinishSignUp()
    }
}