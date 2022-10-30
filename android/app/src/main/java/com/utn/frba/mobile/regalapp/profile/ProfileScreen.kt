package com.utn.frba.mobile.regalapp.profile

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import com.utn.frba.mobile.regalapp.R
import com.utn.frba.mobile.regalapp.login.AuthenticationActions
import com.utn.frba.mobile.regalapp.login.AuthenticationState
import com.utn.frba.mobile.regalapp.login.AuthenticationViewModel

@Composable
fun ProfileScreen(viewModel: AuthenticationViewModel) {
    val state = viewModel.observeState().collectAsState(initial = AuthenticationState()).value

    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        // ACA LOS CAMPOS DEBERIAN TENER VALORES POR DEFAULT CON LOS QUE ESTAN EN LA BD, EL USUARIO SOLO LOS MODIFICA Y AL APRETAR EL BOTON "GUARDAR"
        // SE ACTUALIZAN EN LA BD


        // FOTO DE PERFIL
        //Image()

        // reg_firstname
        TextField(value = state.user_firstname.orEmpty(), onValueChange = {
            viewModel.action(AuthenticationActions.SetRegFirstname(it))
        })
        // reg_lastname
        TextField(value = state.user_lastname.orEmpty(), onValueChange = {
            viewModel.action(AuthenticationActions.SetRegLastname(it))
        })
        // reg_username
        TextField(value = state.user_username.orEmpty(), onValueChange = {
            viewModel.action(AuthenticationActions.SetRegUsername(it))
        })
        // reg_email
        TextField(value = state.user_email.orEmpty(), onValueChange = {
            viewModel.action(AuthenticationActions.SetRegEmail(it))
        })
        // reg_password
        TextField(value = state.user_password.orEmpty(), onValueChange = {
            viewModel.action(AuthenticationActions.SetRegPassword(it))
        })

        // BOTON "GUARDAR" QUE ACTUALIZA LOS VALORES EN LA BD Y TE RE-DIRIGE AL HOME
        Button(onClick = {

        }) {
            viewModel.action(AuthenticationActions.LoginClicked)
        }
    }
}