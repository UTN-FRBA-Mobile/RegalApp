package com.utn.frba.mobile.regalapp

import androidx.fragment.app.Fragment
import com.squareup.anvil.annotations.ContributesMultibinding
import com.utn.frba.mobile.domain.di.ActivityScope
import com.utn.frba.mobile.domain.di.FragmentKey
import javax.inject.Inject

@FragmentKey(EventsFragment::class)
@ContributesMultibinding(ActivityScope::class, Fragment::class)
class EventsFragment @Inject constructor() : Fragment() {
    // TODO
}
