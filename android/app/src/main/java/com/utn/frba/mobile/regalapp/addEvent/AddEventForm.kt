package com.utn.frba.mobile.regalapp.addEvent

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
import com.utn.frba.mobile.regalapp.R
import java.util.*

@Composable
fun AddEventForm(viewModel: AddEventViewModel) {
    val state = viewModel.observeState().collectAsState(initial = AddEventState()).value
    val handleDateChange = { newDate: String ->
        viewModel.action(AddEventActions.SetDate(newDate))
    }
    val handleImageChange = { newImage: String ->
        viewModel.action(AddEventActions.SetImage(newImage))
    }

    Scaffold(
        content = {
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
                    value = state.name.orEmpty(),
                    onValueChange = {
                        viewModel.action(AddEventActions.SetName(it))
                    }
                )
                Spacer(modifier = Modifier.height(40.dp))
                DatePicker(state.date, handleDateChange)
                Spacer(modifier = Modifier.height(60.dp))
                Button(onClick = {
                    viewModel.action(AddEventActions.SaveEventClicked)
                }) {
                    Text(text = stringResource(id = R.string.create))
                }
            }
        }
    )
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
    val mContext = LocalContext.current

    val mYear: Int
    val mMonth: Int
    val mDay: Int

    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // Initialize state
    onDateChange("$mDay/${mMonth+1}/$mYear")

    // Declaring DatePickerDialog and setting initial values as current
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            onDateChange("$mDayOfMonth/${mMonth+1}/$mYear")
        }, mYear, mMonth, mDay
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
            mDatePickerDialog.show()
        }
    )
}
