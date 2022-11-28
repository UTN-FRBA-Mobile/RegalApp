package com.utn.frba.mobile.regalapp.login

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.utn.frba.mobile.domain.repositories.events.EventsRepository
import com.utn.frba.mobile.regalapp.MainActivity
import com.utn.frba.mobile.regalapp.R
import com.utn.frba.mobile.regalapp.RegalApplication
import com.utn.frba.mobile.regalapp.di.DaggerFragmentFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class AuthActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: AuthenticationViewModel.Factory

    @Inject
    lateinit var daggerFragmentFactory: DaggerFragmentFactory

    @Inject
    lateinit var eventsRepository: EventsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDagger()
        supportFragmentManager.fragmentFactory = daggerFragmentFactory
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
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
