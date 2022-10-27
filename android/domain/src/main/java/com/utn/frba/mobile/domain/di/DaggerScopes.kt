package com.utn.frba.mobile.domain.di

import javax.inject.Scope

@Retention(value = AnnotationRetention.RUNTIME)
@Scope
annotation class AppScope

@Retention(value = AnnotationRetention.RUNTIME)
@Scope
annotation class ActivityScope

@Retention(value = AnnotationRetention.RUNTIME)
@Scope
annotation class UserScope
