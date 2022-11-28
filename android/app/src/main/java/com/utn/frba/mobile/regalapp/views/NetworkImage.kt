package com.utn.frba.mobile.regalapp.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.constraintlayout.compose.ConstraintLayout
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import timber.log.Timber

@Composable
fun NetworkImage(
    url: String,
    modifier: Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    GlideImage(
        imageModel = url,
        modifier = modifier,
        imageOptions = ImageOptions(contentScale = contentScale),
        loading = {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val indicator = createRef()
                CircularProgressIndicator(
                    modifier = Modifier.constrainAs(indicator) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
            }
        },
        failure = {
            Timber.e(it.reason)
            Text(
                text = "Fallo en la carga de imagen",
                style = MaterialTheme.typography.body2
            )
        }
    )
}