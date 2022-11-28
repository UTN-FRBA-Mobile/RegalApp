package com.utn.frba.mobile.regalapp.eventDetail

import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import androidx.compose.foundation.Image
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.DatePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.utn.frba.mobile.domain.models.EventModel
import com.utn.frba.mobile.regalapp.R
import com.utn.frba.mobile.regalapp.eventList.EventsActions
import com.utn.frba.mobile.regalapp.eventList.EventsState
import com.utn.frba.mobile.regalapp.eventList.EventsViewModel
import timber.log.Timber
import java.util.*

@Composable
fun EditEventForm(viewModel: EventsViewModel) {
    val state = viewModel.observeState().collectAsState(initial = EventsState()).value.selectedEvent

    Timber.i("Estado - fecha", state?.date.orEmpty())
    Timber.i("Estado - nombre", state?.name.orEmpty())

    val handleNameChange = { newName: String ->
        viewModel.action(EventsActions.SetName(newName))
    }

    val handleDateChange = { newDate: String ->
        viewModel.action(EventsActions.SetDate(newDate))
    }

    val handleImageChange = { newImage: String ->
        viewModel.action(EventsActions.SetImage(newImage))
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        ImagePicker(handleImageChange)
        Spacer(modifier = Modifier.height(40.dp))
        TextField(
            label = {
                Text(stringResource(id = R.string.name))
            },
            value = state?.name ?: "",
            onValueChange = {
                handleNameChange(it)
            }
        )
        Spacer(modifier = Modifier.height(40.dp))
        DatePicker(state?.date ?: "", handleDateChange)
        Spacer(modifier = Modifier.height(60.dp))
        Button(onClick = {
            viewModel.action(EventsActions.UpdateEventClicked(EventModel(
                name = state?.name ?: "",
                date = state?.date ?: "",
                id = state?.id ?: "",
                ownerId = state?.ownerId ?: ""
            )))
        }) {
            Text(text = stringResource(id = R.string.edit))
        }
    }
}

@Composable
fun ImagePicker(onImageChange: (newImage: String) -> Any) {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val bitmap =  remember {
        mutableStateOf<Bitmap?>(null)
    }
    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    Column() {
        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }
            // TODO: Revisar
            onImageChange("newImage")
        }
        val paint = bitmap.value.let { bm ->
            if (bm !== null)
                BitmapPainter(bm.asImageBitmap())
            else painterResource(
                R.drawable.user_icon_placeholder
            )
        }
        Image(
            painter = paint,
            contentDescription = stringResource(id = R.string.new_event_image),
            Modifier
                .size(180.dp)
                .clip(CircleShape)
                .clickable { launcher.launch("image/*") },
            contentScale = ContentScale.Crop
        )
    }
}


@Composable
fun DatePicker(selectedDate: String?, onDateChange: (newDate: String) -> Any){
    val context = LocalContext.current

    val year: Int
    val month: Int
    val day: Int

    val calendar = Calendar.getInstance()

    // Fetching current year, month and day
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)

    // Declaring DatePickerDialog and setting initial values as current
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            onDateChange("$dayOfMonth/${month+1}/$year")
        }, year, month, day
    )

    TextField(
        label = {
            Text(stringResource(id = R.string.goal_date))
        },
        value = selectedDate.orEmpty(),
        onValueChange = {
            onDateChange(it)
        },
        enabled = false,
        colors = TextFieldDefaults.textFieldColors(
            disabledTextColor = MaterialTheme.colors.onSurface,
            disabledLabelColor =  MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
        ),
        modifier = Modifier.clickable {
            datePickerDialog.show()
        }
    )
}
