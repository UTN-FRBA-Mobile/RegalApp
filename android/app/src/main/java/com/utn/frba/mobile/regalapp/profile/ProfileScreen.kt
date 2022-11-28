package com.utn.frba.mobile.regalapp.login

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.utn.frba.mobile.regalapp.map.ProfileState
import com.utn.frba.mobile.regalapp.map.ProfileViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(viewModel: ProfileViewModel, logout: () -> Unit) {
    val state = viewModel.observeState()
        .collectAsState(initial = ProfileState()).value
    val user = state.user
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Email
        Row() {
            Spacer(modifier = Modifier.weight(1f))

            MostrarEmail(
                user?.email.orEmpty()
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        Row() {
            Spacer(modifier = Modifier.weight(1f))

            MostrarNombre(
                user?.name.orEmpty()
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        Row() {
            Spacer(modifier = Modifier.weight(1f))

            MostrarApellido(
                user?.lastName.orEmpty()
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        Button(onClick = {
            logout()
        }) {
            Text("Log out")
        }

        // Password
        /* TextField(
            value = state.password.orEmpty(),
            label = {
                Text(stringResource(R.string.password))
            },
            modifier = Modifier.padding(top = 8.dp),
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = {
                viewModel.action(AuthenticationActions.SetPassword(it))
            }
        )

        Button(onClick = {
            viewModel.action(AuthenticationActions.LoginClicked)
        }) {
            Text("GUARDAR CAMBIOS")
        }

        if (state.isLoading) {
            CircularProgressIndicator()
        }  */
    }
}

@Composable
fun MostrarEmail(
    value: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = { },
        label = { Text("Email") },
        readOnly = true
    )
}
@Composable
fun MostrarNombre(
    value: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = { },
        label = { Text("Nombre") },
        readOnly = true
    )
}
@Composable
fun MostrarApellido(
    value: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = { },
        label = { Text("Apellido") },
        readOnly = true
    )
}


