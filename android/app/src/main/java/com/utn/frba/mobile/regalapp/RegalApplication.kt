package com.utn.frba.mobile.regalapp

import android.app.Application
import com.utn.frba.mobile.regalapp.di.AppComponent
import com.utn.frba.mobile.regalapp.di.DaggerAppComponent
import com.utn.frba.mobile.regalapp.di.UserComponent

class RegalApplication : Application() {

    lateinit var appComponent: AppComponent
    private var userComponent: UserComponent? = null

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory().create(this)
    }

    @Synchronized
    fun getUserComponent(): UserComponent {
        if (userComponent == null) {
            userComponent = appComponent.userComponent.create()
        }
        return userComponent!!
    }
}
