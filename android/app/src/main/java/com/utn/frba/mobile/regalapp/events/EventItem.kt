package com.utn.frba.mobile.regalapp.events

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.utn.frba.mobile.regalapp.R

@Composable
fun EventItem(event: EventModel) {

    Row(
        Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.width(20.dp))
        Button(
            onClick = {},
            Modifier.weight(1F),
        ) {
            Row(
                Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(
                        R.drawable.event_placeholder
                    ),
                    contentDescription = event.name,
                    Modifier
                        .size(
                            64.dp
                        )
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop, // crop the image if it's not a square
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,

                ) {
                    Text(text = event.name)
                    Text(
                        text = stringResource(id = R.string.items_bought, 2, 3)
                    )
                }
                Spacer(modifier = Modifier.weight(1F))
            }
        }
        Spacer(modifier = Modifier.width(20.dp))
    }
}
