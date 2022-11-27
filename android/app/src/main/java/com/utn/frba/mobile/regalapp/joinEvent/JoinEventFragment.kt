package com.utn.frba.mobile.regalapp.joinEvent


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.squareup.anvil.annotations.ContributesMultibinding
import com.utn.frba.mobile.domain.di.ActivityScope
import com.utn.frba.mobile.domain.di.FragmentKey
import com.utn.frba.mobile.regalapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@FragmentKey(JoinEventFragment::class)
@ContributesMultibinding(ActivityScope::class, Fragment::class)
class JoinEventFragment @Inject constructor(
    private val viewModelFactory: JoinEventViewModel.Factory
) : Fragment() {

    private val args: JoinEventFragmentArgs by navArgs()
    private val viewModel: JoinEventViewModel by navGraphViewModels(R.id.navigation_main) { viewModelFactory }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.action(JoinEventActions.LoadEventDetails(args.eventId))
        observeEvents()
    }

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
                    val state =
                        viewModel.observeState()
                            .collectAsState(initial = JoinEventState.Loading).value

                    when (state) {
                        is JoinEventState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }

                        }
                        is JoinEventState.EventLoaded -> {
                            JoinEventScreen(
                                args.invitedBy,
                                state.event.name,
                                true,
                                ::goBack,
                                viewModel::action
                            )
                        }
                        is JoinEventState.Error -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "Ocurrió un error intentando obtener los detalles del evento")
                            }
                        }
                    }
                }
            }
        }
    }

    private fun observeEvents() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.observeEvents()
                    .flowOn(Dispatchers.Main)
                    .collect { event ->
                        when (event) {
                            is JoinEventEvents.NavigateToEvent -> {
                                findNavController().navigate(
                                    JoinEventFragmentDirections.openItemsListFromJoinEvent(
                                        event.eventId,
                                        ""
                                    )
                                )
                            }
                        }
                    }
            }
        }
    }

    private fun goBack() {
        findNavController().popBackStack()
    }
}