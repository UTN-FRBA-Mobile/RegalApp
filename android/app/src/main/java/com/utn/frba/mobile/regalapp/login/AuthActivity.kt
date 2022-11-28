package com.utn.frba.mobile.regalapp.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.utn.frba.mobile.domain.repositories.events.EventsRepository
import com.utn.frba.mobile.regalapp.R
import com.utn.frba.mobile.regalapp.RegalApplication
import com.utn.frba.mobile.regalapp.di.DaggerFragmentFactory
import javax.inject.Inject

class AuthActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: AuthenticationViewModel.Factory

    @Inject
    lateinit var eventsRepository: EventsRepository

    @Inject
    lateinit var daggerFragmentFactory: DaggerFragmentFactory

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
