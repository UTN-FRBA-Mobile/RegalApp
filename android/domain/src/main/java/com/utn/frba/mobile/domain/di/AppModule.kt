package com.utn.frba.mobile.domain.di

import android.app.Application
import android.content.Context
import com.squareup.anvil.annotations.ContributesTo
import com.utn.frba.mobile.domain.di.qualifiers.AppContext
import dagger.Module
import dagger.Provides

@Module
@ContributesTo(AppScope::class)
class AppModule {
    @Provides
    @AppContext
    fun provideContext(app: Application): Context {
        return app
    }
}
