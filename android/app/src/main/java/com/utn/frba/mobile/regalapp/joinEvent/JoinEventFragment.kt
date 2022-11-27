package com.utn.frba.mobile.regalapp.joinEvent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.squareup.anvil.annotations.ContributesMultibinding
import com.utn.frba.mobile.domain.di.ActivityScope
import com.utn.frba.mobile.domain.di.FragmentKey
import javax.inject.Inject

@FragmentKey(JoinEventFragment::class)
@ContributesMultibinding(ActivityScope::class, Fragment::class)
class JoinEventFragment @Inject constructor(): Fragment() {

    private val args: JoinEventFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    JoinEventScreen(
                        args.invitedBy,
                        "cumpleañito", // TODO get event name
                        true,
                        ::goBack
                    )
                }
            }
        }
    }

    private fun goBack() {
        findNavController().popBackStack()
    }
}