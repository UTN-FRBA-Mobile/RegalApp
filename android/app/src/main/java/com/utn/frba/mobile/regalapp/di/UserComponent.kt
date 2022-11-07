package com.utn.frba.mobile.regalapp.di

import com.squareup.anvil.annotations.MergeSubcomponent
import com.utn.frba.mobile.domain.di.UserScope
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
