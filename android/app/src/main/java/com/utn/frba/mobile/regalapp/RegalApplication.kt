package com.utn.frba.mobile.regalapp

import android.app.Application
import com.utn.frba.mobile.regalapp.di.AppComponent
import com.utn.frba.mobile.regalapp.di.DaggerAppComponent

class RegalApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory().create(this)
    }
}
