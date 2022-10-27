package com.utn.frba.mobile.regalapp.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.navigation.navGraphViewModels
import com.utn.frba.mobile.regalapp.MainActivity
import com.utn.frba.mobile.regalapp.R
import com.utn.frba.mobile.regalapp.RegalApplication
import com.utn.frba.mobile.regalapp.eventList.EventsViewModel
import javax.inject.Inject

class LoginActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: AuthenticationViewModel.Factory

    private val viewModel: AuthenticationViewModel by viewModels<AuthenticationViewModel> { viewModelFactory }


    override fun onCreate(savedInstanceState: Bundle?) {
        injectDagger()
        super.onCreate(savedInstanceState)
        setContent {
            val isLoggedIn = viewModel.observeState()
                .collectAsState(initial = AuthenticationState()).value.isLoggedIn
            if (isLoggedIn) {
                startActivity(Intent(this, MainActivity::class.java))
            }
            LoginScreen(viewModel = viewModel)
        }
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