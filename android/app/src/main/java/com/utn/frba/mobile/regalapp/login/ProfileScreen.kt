package com.utn.frba.mobile.regalapp.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen(viewModel: AuthenticationViewModel) {
    val state = viewModel.observeState()
        .collectAsState(initial = AuthenticationState())

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

            MostrarEmail()

            //Text(text = "VALOR ACTUAL DE EMAIL DE SESION")
            /*TextField(
                label = {
                    Text(stringResource(R.string.email))
                },
                value = state.email.orEmpty(),
                //placeholder = { Text(text = "VALOR ACTUAL DE EMAIL") },
                onValueChange = {
                    //viewModel.action(AuthenticationActions.SetEmail(it))

                }
            )*/
            Spacer(modifier = Modifier.weight(1f))
        }
        Row() {
            Spacer(modifier = Modifier.weight(1f))

            MostrarNombre()
            Spacer(modifier = Modifier.weight(1f))
        }
        Row() {
            Spacer(modifier = Modifier.weight(1f))

            MostrarApellido()
            Spacer(modifier = Modifier.weight(1f))
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
fun MostrarEmail() {
    OutlinedTextField(
        value = email,
        onValueChange = { },
        label = { Text("Email") },
        readOnly = true
    )
}
@Composable
fun MostrarNombre() {
    OutlinedTextField(
        value = "Gabriel",
        onValueChange = { },
        label = { Text("Nombre") },
        readOnly = true
    )
}
@Composable
fun MostrarApellido() {
    OutlinedTextField(
        value = "Alvarez",
        onValueChange = { },
        label = { Text("Apellido") },
        readOnly = true
    )
}


