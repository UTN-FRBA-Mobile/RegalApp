package com.utn.frba.mobile.regalapp.joinEvent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.utn.frba.mobile.regalapp.R
import io.github.fededri.arch.interfaces.ActionsDispatcher

@Composable
fun JoinEventScreen(
    invitedBy: String,
    eventName: String,
    isLoggedIn: Boolean,
    onBackClicked: () -> Unit,
    actionsDispatcher: (JoinEventActions) -> Unit
) {
    val checked = remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            JoinEventTopBar(eventName, onBackClicked)
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {

                Text(
                    text = stringResource(
                        id = R.string.you_have_been_invited_to,
                        invitedBy.replaceFirstChar { it.uppercaseChar() },
                        eventName
                    ),
                    modifier = Modifier.padding(bottom = 8.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = checked.value, onCheckedChange = {
                        checked.value = it
                        actionsDispatcher(JoinEventActions.SetEnablePushNotifications(it))
                    })

                    Text(text = stringResource(R.string.confirm_event_notifications))
                }

                if (isLoggedIn) {
                    Button(onClick = { actionsDispatcher(JoinEventActions.JoinEvent) }) {
                        Text(text = "Unirme")
                    }
                } else {
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Registrarse")
                    }
                }
            }
        }
    )
}

@Composable
private fun JoinEventTopBar(
    eventName: String,
    onBackClicked: () -> Unit
) {
    TopAppBar(title = {
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = stringResource(id = R.string.join_to_event_title, eventName))
    },
        navigationIcon = {
            IconButton(onClick = onBackClicked) {
                Image(
                    painter = painterResource(id = R.drawable.window_close),
                    contentDescription = stringResource(id = R.string.user_image_content_description)
                )
            }
        }
    )
}