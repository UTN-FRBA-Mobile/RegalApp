package com.utn.frba.mobile.regalapp.events

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.utn.frba.mobile.regalapp.R


@Composable
fun EventItem(event: EventModel) {


    Row(
        Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.width(Dp(20F)))
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
                            Dp(64F)
                        )
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,            // crop the image if it's not a square
                )
                Spacer(modifier = Modifier.width(Dp(10F)))
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,

                    ) {
                    Text(text = event.name)
                    Text(text = "X/X comprados")
                }
                Spacer(modifier = Modifier.weight(1F))
            }

        }
        Spacer(modifier = Modifier.width(Dp(20F)))
    }
}
