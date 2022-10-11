package com.utn.frba.mobile.regalapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.utn.frba.mobile.domain.models.NetworkResponse
import com.utn.frba.mobile.domain.repositories.auth.UserRepository
import com.utn.frba.mobile.regalapp.di.DaggerFragmentFactory
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var daggerFragmentFactory: DaggerFragmentFactory

    // TODO remove this when we split the app logic properly, just for testing purposes
    @Inject
    lateinit var userRepository: UserRepository
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
        application.appComponent
            .activityComponentFactory
            .create()
            .inject(this)
    }

    private fun createRandomAccount() {
        // TODO remove, just for testing
        val email = "asd3@gmail.com"
        lifecycleScope.launchWhenResumed {
            when (val response = userRepository.createAccount(email, "123456")) {
                is NetworkResponse.Success -> {
                    val uid = response.data?.user?.uid
                    Timber.i("User created! uid: $uid")
                }
                is NetworkResponse.Error -> {
                    Timber.e("Something went wrong")
                }
            }
        }
    }
}
