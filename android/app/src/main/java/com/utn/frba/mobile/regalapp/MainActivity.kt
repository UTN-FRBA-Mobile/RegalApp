package com.utn.frba.mobile.regalapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.utn.frba.mobile.domain.dataStore.UserDataStore
import com.utn.frba.mobile.domain.models.NetworkResponse
import com.utn.frba.mobile.domain.repositories.auth.UserRepository
import com.utn.frba.mobile.regalapp.di.DaggerFragmentFactory
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var daggerFragmentFactory: DaggerFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDagger()
        supportFragmentManager.fragmentFactory = daggerFragmentFactory
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun injectDagger() {
        val application = application
        check(application is RegalApplication) {
            "Application is not a custom class"
        }
        application.getUserComponent()
            .activityComponentFactory
            .create()
            .inject(this)
    }
}
