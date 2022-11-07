package com.utn.frba.mobile.regalapp.eventDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.squareup.anvil.annotations.ContributesMultibinding
import com.utn.frba.mobile.domain.di.ActivityScope
import com.utn.frba.mobile.domain.di.FragmentKey
import javax.inject.Inject

@FragmentKey(EventDetailFragment::class)
@ContributesMultibinding(ActivityScope::class, Fragment::class)
class EventDetailFragment @Inject constructor() : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                // TODO: Remove placeholder, add content
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    Text(text = "EVENT DETAIL")
                }
            }
        }
    }
}
