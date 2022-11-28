package com.utn.frba.mobile.regalapp.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.squareup.anvil.annotations.ContributesMultibinding
import com.utn.frba.mobile.domain.di.ActivityScope
import com.utn.frba.mobile.domain.di.FragmentKey
import com.utn.frba.mobile.domain.repositories.auth.UserRepository
import com.utn.frba.mobile.regalapp.login.AuthActivity
import com.utn.frba.mobile.regalapp.views.NetworkImage
import kotlinx.coroutines.launch
import javax.inject.Inject

@FragmentKey(ProfileFragment::class)
@ContributesMultibinding(ActivityScope::class, Fragment::class)
class ProfileFragment @Inject constructor() : Fragment() {

    @Inject
    lateinit var userRepository: UserRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column() {
                            NetworkImage(
                                url = "https://learn.microsoft.com/answers/storage/attachments/209536-360-f-364211147-1qglvxv1tcq0ohz3fawufrtonzz8nq3e.jpg",
                                modifier = Modifier
                                    .width(40.dp)
                                    .height(40.dp)
                            )

                            Button(onClick = {
                                lifecycleScope.launch {
                                    userRepository.logout()
                                    startActivity(Intent(requireActivity(), AuthActivity::class.java))
                                }
                            }) {
                                Text("Log out")
                            }
                        }
                    }
                }
            }
        }
    }
}