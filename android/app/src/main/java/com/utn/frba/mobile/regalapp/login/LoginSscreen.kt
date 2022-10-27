package com.utn.frba.mobile.regalapp.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(viewModel: AuthenticationViewModel) {
    val state = viewModel.observeState().collectAsState(initial = AuthenticationState()).value

    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        // Email
        TextField(value = state.email.orEmpty(), onValueChange = {
            viewModel.action(AuthenticationActions.SetEmail(it))
        })

        // Password
        TextField(value = state.password.orEmpty(), onValueChange = {
            viewModel.action(AuthenticationActions.SetPassword(it))
        })

        Button(onClick = {

        }) {
            viewModel.action(AuthenticationActions.LoginClicked)
        }
    }
}