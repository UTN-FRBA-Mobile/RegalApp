package com.utn.frba.mobile.regalapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.utn.frba.mobile.domain.models.NetworkResponse
import com.utn.frba.mobile.domain.repositories.auth.UserRepository
import com.utn.frba.mobile.regalapp.di.DaggerFragmentFactory
import com.utn.frba.mobile.regalapp.ui.theme.RegalappTheme
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
        setContent {
            RegalappTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HomeScreen()
                }
            }
        }
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

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RegalappTheme {
        Greeting("Android")
    }
}
