package com.utn.frba.mobile.regalapp

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
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
        if(
            ActivityCompat.checkSelfPermission(
                this,
                ACCESS_COARSE_LOCATION
            ) != PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    ACCESS_COARSE_LOCATION
                ),
                99
            )
        }
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
