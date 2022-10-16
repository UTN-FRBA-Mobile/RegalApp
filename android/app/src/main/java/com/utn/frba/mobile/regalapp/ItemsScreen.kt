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
import com.utn.frba.mobile.regalapp.events.EventModel
import com.utn.frba.mobile.regalapp.events.ItemList
import com.utn.frba.mobile.regalapp.events.ItemModel

@Preview(showBackground = true)
@Composable
fun ItemScreen() {
    val items = mutableListOf<ItemModel>(
        ItemModel("Vasos"),
        ItemModel("Bebidas", status = true, bought_by = "Gonzalo"),
        ItemModel("Carne")
    )
    val event = EventModel(
        name = "Asado",
        items = items,
    )
    Scaffold(
        topBar = {
            ItemsScreenTopBar(event)
        },
        content = { innerPadding ->
            ItemList(items = event.items, contentPadding = innerPadding)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {  }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "new-item")
            }
        }
    )
}

@Composable
fun ItemsScreenTopBar(event: EventModel) {
    TopAppBar(title = {
        Image(
            painter = painterResource(id = R.drawable.event_placeholder),
            contentDescription = event.name,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.White)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = event.name)
    })
}
