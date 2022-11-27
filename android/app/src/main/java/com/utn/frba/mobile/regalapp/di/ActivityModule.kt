package com.utn.frba.mobile.regalapp.di

import android.content.Context
import com.squareup.anvil.annotations.ContributesTo
import com.utn.frba.mobile.domain.di.ActivityScope
import com.utn.frba.mobile.domain.di.qualifiers.ActivityContext
import com.utn.frba.mobile.regalapp.MainActivity
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

@Module
@ContributesTo(ActivityScope::class)
class ActivityModule {
    @Provides
    @ActivityContext
    fun provideActivityContext(mainActivity: MainActivity): Context {
        return mainActivity
    }
}