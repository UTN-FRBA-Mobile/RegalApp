package com.utn.frba.mobile.regalapp.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import javax.inject.Inject
import javax.inject.Provider

class DaggerFragmentFactory @Inject constructor(
    private val fragmentProviderMap: Map<Class<out Fragment>, @JvmSuppressWildcards Provider<Fragment>>
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        val clazz = classLoader.loadClass(className)
        var creator = fragmentProviderMap[clazz]

        if (creator == null) {
            creator = fragmentProviderMap
                .asIterable()
                .firstOrNull { clazz.isAssignableFrom(it.key) }
                ?.value
        }

        return if (creator != null) {
            creator.get()
        } else {
            super.instantiate(classLoader, className)
        }
    }
}
