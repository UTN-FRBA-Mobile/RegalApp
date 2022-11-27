package com.utn.frba.mobile.regalapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.utn.frba.mobile.regalapp.di.DaggerFragmentFactory
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
            .create(this)
            .inject(this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }
}
