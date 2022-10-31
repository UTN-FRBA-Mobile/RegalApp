package com.utn.frba.mobile.regalapp.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navGraphViewModels
import com.utn.frba.mobile.domain.models.EventFields
import com.utn.frba.mobile.domain.models.EventModel
import com.utn.frba.mobile.domain.models.ItemModel
import com.utn.frba.mobile.domain.repositories.events.EventsRepository
import com.utn.frba.mobile.regalapp.MainActivity
import com.utn.frba.mobile.regalapp.R
import com.utn.frba.mobile.regalapp.RegalApplication
import com.utn.frba.mobile.regalapp.eventList.EventsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class LoginActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: AuthenticationViewModel.Factory

    @Inject
    lateinit var eventsRepository: EventsRepository

    private val viewModel: AuthenticationViewModel by viewModels<AuthenticationViewModel> { viewModelFactory }


    override fun onCreate(savedInstanceState: Bundle?) {
        injectDagger()
        super.onCreate(savedInstanceState)
        setContent {
            val initialState = AuthenticationState()
            val state = viewModel.observeState()
                .collectAsState(initial = initialState)
            val isLoggedIn = state.value.isLoggedIn
            if (isLoggedIn) {
                startActivity(Intent(this, MainActivity::class.java))
            }
            LoginScreen(viewModel = viewModel)
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            viewModel.observeEvents()
                .onEach {
                    Timber.i("received event")
                }
                .flowOn(Dispatchers.Main)
                .collect { event ->
                    when(event) {
                        is AuthenticationEvents.LoginFailed -> showInvalidLoginAlert()
                        is AuthenticationEvents.MissingFields -> showMissingFieldsAlert()
                    }
                }
        }
    }

    private fun showMissingFieldsAlert() {
        AlertDialog.Builder(this)
            .setMessage("Some fields are missing, enter both username and password")
            .setTitle(getString(R.string.login_failed))
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showInvalidLoginAlert() {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.login_failed_message))
            .setTitle(getString(R.string.login_failed))
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun injectDagger() {
        val application = application
        check(application is RegalApplication) {
            "Application is not a custom class"
        }
        application.appComponent
            .inject(this)
    }
}