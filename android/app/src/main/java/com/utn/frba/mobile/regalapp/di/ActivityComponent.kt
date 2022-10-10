package com.utn.frba.mobile.regalapp.di

import com.squareup.anvil.annotations.MergeSubcomponent
import com.utn.frba.mobile.domain.di.ActivityScope
import com.utn.frba.mobile.regalapp.MainActivity
import dagger.Subcomponent

@ActivityScope
@MergeSubcomponent(ActivityScope::class)
interface ActivityComponent {
    fun inject(activity: MainActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ActivityComponent
    }
}
