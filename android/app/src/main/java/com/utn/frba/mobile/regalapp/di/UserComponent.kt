package com.utn.frba.mobile.regalapp.di

import com.squareup.anvil.annotations.MergeSubcomponent
import com.utn.frba.mobile.domain.di.ActivityScope
import com.utn.frba.mobile.domain.di.UserScope
import com.utn.frba.mobile.regalapp.MainActivity
import com.utn.frba.mobile.regalapp.login.LoginActivity
import dagger.Subcomponent

@UserScope
@MergeSubcomponent(UserScope::class)
interface UserComponent {
    val activityComponentFactory: ActivityComponent.Factory

    @Subcomponent.Factory
    interface Factory {
        fun create(): UserComponent
    }
}