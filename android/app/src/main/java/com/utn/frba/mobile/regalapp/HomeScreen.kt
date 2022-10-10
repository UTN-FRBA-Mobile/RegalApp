package com.utn.frba.mobile.regalapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.utn.frba.mobile.regalapp.events.EventList
import com.utn.frba.mobile.regalapp.events.EventModel

@Preview(showBackground = true)
@Composable
fun HomeScreen() {
    val events = mutableListOf<EventModel>(
        EventModel("Mi cumple"),
        EventModel("Asado"),
        EventModel("Algo aburrido")
    )
    Scaffold(
        topBar = {
             HomeScreenTopBar()
        },
        content = { innerPadding ->
            EventList(events = events, contentPadding = innerPadding)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "new-item")
            }
        }
    )
}

@Composable
fun HomeScreenTopBar() {
    TopAppBar(title = {
        Image(
            painter = painterResource(id = R.drawable.user_icon_placeholder),
            contentDescription = stringResource(id = R.string.user_image_content_description),
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.White)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = stringResource(id = R.string.home_screen_title))
    })
}
