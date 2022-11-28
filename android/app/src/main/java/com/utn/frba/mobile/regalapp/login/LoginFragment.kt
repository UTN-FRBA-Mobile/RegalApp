package com.utn.frba.mobile.regalapp.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.squareup.anvil.annotations.ContributesMultibinding
import com.utn.frba.mobile.domain.di.AppScope
import com.utn.frba.mobile.domain.di.FragmentKey
import com.utn.frba.mobile.regalapp.MainActivity
import com.utn.frba.mobile.regalapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@FragmentKey(LoginFragment::class)
@ContributesMultibinding(AppScope::class, Fragment::class)
class LoginFragment @Inject constructor(
    private val viewModelFactory: AuthenticationViewModel.Factory
) : Fragment() {

    private val viewModel: AuthenticationViewModel by navGraphViewModels(R.id.auth_navigation_graph) { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val initialState = AuthenticationState()
                    val state = viewModel.observeState()
                        .collectAsState(initial = initialState)
                    val isLoggedIn = state.value.isLoggedIn
                    if (isLoggedIn) {
                        startActivity(Intent(requireActivity(), MainActivity::class.java))
                    }
                    LoginScreen(viewModel = viewModel)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeEvents()
    }

    private fun observeEvents() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.observeEvents()
                    .flowOn(Dispatchers.Main)
                    .collect {
                        when (it) {
                            is AuthenticationEvents.LoginFailed -> showInvalidLoginAlert()
                            is AuthenticationEvents.MissingFields -> showMissingFieldsAlert()
                            is AuthenticationEvents.PasswordsNotEquals -> showPasswordsAlert()
                            is AuthenticationEvents.NavigateToSignUp -> {
                                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
                            }
                            else -> {}
                        }
                    }
            }
        }
    }

    private fun showMissingFieldsAlert() {
        AlertDialog.Builder(requireContext())
            .setMessage("Por favor completar todos los campos solicitados!")
            .setTitle(getString(R.string.login_failed))
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    private fun showPasswordsAlert() {
        AlertDialog.Builder(requireContext())
            .setMessage("Las contraseñas ingresadas no coinciden!")
            .setTitle("Registro fallido")
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showInvalidLoginAlert() {
        AlertDialog.Builder(requireContext())
            .setMessage("Datos ingresados son incorrectos!")
            .setTitle(getString(R.string.login_failed))
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

}