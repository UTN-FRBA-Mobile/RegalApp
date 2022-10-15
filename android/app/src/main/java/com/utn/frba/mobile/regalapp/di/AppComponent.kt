package com.utn.frba.mobile.regalapp.di

import android.app.Application
import com.squareup.anvil.annotations.MergeComponent
import com.utn.frba.mobile.domain.di.AppScope
import dagger.BindsInstance
import dagger.Component

@AppScope
@MergeComponent(AppScope::class)
interface AppComponent {
    val application: Application
    val activityComponentFactory: ActivityComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}
