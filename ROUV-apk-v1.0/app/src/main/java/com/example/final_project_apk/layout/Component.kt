package com.example.final_project_apk.layout

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.example.final_project_apk.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.final_project_apk.ui.theme.BlueDonker
import com.example.final_project_apk.ui.theme.DarkGray
import com.example.final_project_apk.ui.theme.GreenPastel
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.pointer.pointerInput
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.final_project_apk.network.control.rightanalog.RightAnalogHandler
import com.example.final_project_apk.network.control.leftdpad.LeftDPad
import com.example.final_project_apk.network.control.leftdpad.StepperDirection
import com.example.final_project_apk.network.status.ServerHandler

@Preview
@Composable
fun Home_Bar(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(132.dp),
        contentAlignment = Alignment.BottomStart){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(76.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 9.dp,
                        topEnd = 9.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                )
                .background(color = GreenPastel),
            contentAlignment = Alignment.Center
        ) {
            Row {
                Box {
                    Image(
                        painter = painterResource(R.drawable.home_vector_bar),
                        contentDescription = "Home",
                        modifier = Modifier
                            .size(width = 36.2.dp, height = 39.08.dp)
                            .offset(x = (-1).dp, y = (2).dp)
                            .clip(RoundedCornerShape(5.dp))
                            .blur(4.dp),
                        colorFilter = ColorFilter.tint(GreenPastel)
                    )
                    Image(
                        painter = painterResource(R.drawable.home_vector_bar),
                        contentDescription = "Deskripsi aksesibilitas",
                        modifier = Modifier
                            .size(width = 34.2.dp, height = 37.08.dp)
                            .clip(RectangleShape),
                        colorFilter = ColorFilter.tint(GreenPastel)
                    )
                }
                Spacer(Modifier.width(100.dp))
                Box {
                    Image(
                        painter = painterResource(R.drawable.joystick_vector_bar),
                        contentDescription = "Home",
                        modifier = Modifier
                            .size(width = 36.2.dp, height = 39.08.dp)
                            .offset(x = (-1).dp, y = (2).dp)
                            .clip(RoundedCornerShape(10.dp))
                            .blur(4.dp),
                        colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.25f))
                    )
                    Image(
                        painter = painterResource(R.drawable.joystick_vector_bar),
                        contentDescription = "Deskripsi aksesibilitas",
                        modifier = Modifier
                            .size(width = 34.2.dp, height = 37.08.dp)
                            .clip(RectangleShape)
                    )
                }
                Spacer(Modifier.width(100.dp))
                Box {
                    Image(
                        painter = painterResource(R.drawable.settings_vector_bar),
                        contentDescription = "Home",
                        modifier = Modifier
                            .size(width = 36.2.dp, height = 39.08.dp)
                            .offset(x = (-1).dp, y = (2).dp)
                            .clip(RoundedCornerShape(10.dp))
                            .blur(4.dp),
                        colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.25f))
                    )
                    Image(
                        painter = painterResource(R.drawable.settings_vector_bar),
                        contentDescription = "Deskripsi aksesibilitas",
                        modifier = Modifier
                            .size(width = 34.2.dp, height = 37.08.dp)
                            .clip(RectangleShape)
                    )
                }
            }
        }
        Box(modifier = Modifier
            .wrapContentSize()
            .offset(x=0.dp,y=(-19).dp),
            contentAlignment = Alignment.Center){
            Image(
                painter = painterResource(R.drawable.medium_ellipse_bar),
                contentDescription = "Home",
                modifier = Modifier
                    .size(width = 130.dp, height = 112.5.dp)
            )
            Box(modifier = Modifier.wrapContentSize()) {
                Box(
                    modifier = Modifier
                        .padding(vertical = 3.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .blur(7.dp)
                        .wrapContentSize()
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 74.84.dp, height = 74.84.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .background(color = Color.Black.copy(alpha = 0.25f))
                    )
                }
                Box(
                    modifier = Modifier
                        .size(width = 74.84.dp, height = 74.84.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(color = GreenPastel),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.home_vector_bar),
                        contentDescription = "Home",
                        modifier = Modifier
                            .size(width = 36.2.dp, height = 39.08.dp)
                            .offset(x = 0.dp, y = (2).dp)
                            .clip(RoundedCornerShape(10.dp))
                            .blur(3.dp),
                        colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.25f))
                    )
                    Image(
                        painter = painterResource(R.drawable.home_vector_bar),
                        contentDescription = "Deskripsi aksesibilitas",
                        modifier = Modifier
                            .size(width = 34.2.dp, height = 37.08.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        colorFilter = ColorFilter.tint(BlueDonker)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun Control_Bar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(132.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(76.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 9.dp,
                        topEnd = 9.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                )
                .background(color = GreenPastel),
            contentAlignment = Alignment.Center
        ) {
            Row {
                Box {
                    Image(
                        painter = painterResource(R.drawable.home_vector_bar),
                        contentDescription = "Home",
                        modifier = Modifier
                            .size(width = 36.2.dp, height = 39.08.dp)
                            .offset(x = (-1).dp, y = (2).dp)
                            .clip(RoundedCornerShape(5.dp))
                            .blur(4.dp),
                        colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.25f))
                    )
                    Image(
                        painter = painterResource(R.drawable.home_vector_bar),
                        contentDescription = "Deskripsi aksesibilitas",
                        modifier = Modifier
                            .size(width = 34.2.dp, height = 37.08.dp)
                            .clip(RectangleShape),
                    )
                }
                Spacer(Modifier.width(100.dp))
                Box {
                    Image(
                        painter = painterResource(R.drawable.joystick_vector_bar),
                        contentDescription = "Home",
                        modifier = Modifier
                            .size(width = 36.2.dp, height = 39.08.dp)
                            .offset(x = (-1).dp, y = (2).dp)
                            .clip(RoundedCornerShape(10.dp))
                            .blur(4.dp),
                        colorFilter = ColorFilter.tint(GreenPastel)
                    )
                    Image(
                        painter = painterResource(R.drawable.joystick_vector_bar),
                        contentDescription = "Deskripsi aksesibilitas",
                        modifier = Modifier
                            .size(width = 34.2.dp, height = 37.08.dp)
                            .clip(RectangleShape),
                        colorFilter = ColorFilter.tint(GreenPastel)
                    )
                }
                Spacer(Modifier.width(100.dp))
                Box {
                    Image(
                        painter = painterResource(R.drawable.settings_vector_bar),
                        contentDescription = "Home",
                        modifier = Modifier
                            .size(width = 36.2.dp, height = 39.08.dp)
                            .offset(x = (-1).dp, y = (2).dp)
                            .clip(RoundedCornerShape(10.dp))
                            .blur(4.dp),
                        colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.25f))
                    )
                    Image(
                        painter = painterResource(R.drawable.settings_vector_bar),
                        contentDescription = "Deskripsi aksesibilitas",
                        modifier = Modifier
                            .size(width = 34.2.dp, height = 37.08.dp)
                            .clip(RectangleShape)
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .wrapContentSize()
                .offset(x = 130.dp, y = (-19).dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.medium_ellipse_bar),
                contentDescription = "Home",
                modifier = Modifier
                    .size(width = 130.dp, height = 112.5.dp)
            )
            Box(modifier = Modifier.wrapContentSize()) {
                Box(
                    modifier = Modifier
                        .padding(vertical = 3.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .blur(7.dp)
                        .wrapContentSize()
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 74.84.dp, height = 74.84.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .background(color = Color.Black.copy(alpha = 0.25f))
                    )
                }
                Box(
                    modifier = Modifier
                        .size(width = 74.84.dp, height = 74.84.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(color = GreenPastel),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.joystick_vector_bar),
                        contentDescription = "Home",
                        modifier = Modifier
                            .size(width = 36.2.dp, height = 39.08.dp)
                            .offset(x = 0.dp, y = (2).dp)
                            .clip(RoundedCornerShape(10.dp))
                            .blur(3.dp),
                        colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.25f))
                    )
                    Image(
                        painter = painterResource(R.drawable.joystick_vector_bar),
                        contentDescription = "Deskripsi aksesibilitas",
                        modifier = Modifier
                            .size(width = 34.2.dp, height = 37.08.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        colorFilter = ColorFilter.tint(BlueDonker)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun Settings_Bar(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(132.dp),
        contentAlignment = Alignment.BottomStart){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(76.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 9.dp,
                        topEnd = 9.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                )
                .background(color = GreenPastel),
            contentAlignment = Alignment.Center
        ) {
            Row {
                Box {
                    Image(
                        painter = painterResource(R.drawable.home_vector_bar),
                        contentDescription = "Home",
                        modifier = Modifier
                            .size(width = 36.2.dp, height = 39.08.dp)
                            .offset(x = (-1).dp, y = (2).dp)
                            .clip(RoundedCornerShape(5.dp))
                            .blur(4.dp),
                        colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.25f))
                    )
                    Image(
                        painter = painterResource(R.drawable.home_vector_bar),
                        contentDescription = "Deskripsi aksesibilitas",
                        modifier = Modifier
                            .size(width = 34.2.dp, height = 37.08.dp)
                            .clip(RectangleShape),
                    )
                }
                Spacer(Modifier.width(100.dp))
                Box {
                    Image(
                        painter = painterResource(R.drawable.joystick_vector_bar),
                        contentDescription = "Home",
                        modifier = Modifier
                            .size(width = 36.2.dp, height = 39.08.dp)
                            .offset(x = (-1).dp, y = (2).dp)
                            .clip(RoundedCornerShape(10.dp))
                            .blur(4.dp),
                        colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.25f))
                    )
                    Image(
                        painter = painterResource(R.drawable.joystick_vector_bar),
                        contentDescription = "Deskripsi aksesibilitas",
                        modifier = Modifier
                            .size(width = 34.2.dp, height = 37.08.dp)
                            .clip(RectangleShape)
                    )
                }
                Spacer(Modifier.width(100.dp))
                Box {
                    Image(
                        painter = painterResource(R.drawable.settings_vector_bar),
                        contentDescription = "Home",
                        modifier = Modifier
                            .size(width = 36.2.dp, height = 39.08.dp)
                            .offset(x = (-1).dp, y = (2).dp)
                            .clip(RoundedCornerShape(10.dp))
                            .blur(4.dp),
                        colorFilter = ColorFilter.tint(GreenPastel)
                    )
                    Image(
                        painter = painterResource(R.drawable.settings_vector_bar),
                        contentDescription = "Deskripsi aksesibilitas",
                        modifier = Modifier
                            .size(width = 34.2.dp, height = 37.08.dp)
                            .clip(RectangleShape),
                        colorFilter = ColorFilter.tint(GreenPastel)
                    )
                }
            }
        }
        Box(modifier = Modifier
            .wrapContentSize()
            .offset(x=260.dp,y=(-19).dp),
            contentAlignment = Alignment.Center){
            Image(
                painter = painterResource(R.drawable.medium_ellipse_bar),
                contentDescription = "Home",
                modifier = Modifier
                    .size(width = 130.dp, height = 112.5.dp)
            )
            Box(modifier = Modifier.wrapContentSize()) {
                Box(
                    modifier = Modifier
                        .padding(vertical = 3.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .blur(7.dp)
                        .wrapContentSize()
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 74.84.dp, height = 74.84.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .background(color = Color.Black.copy(alpha = 0.25f))
                    )
                }
                Box(
                    modifier = Modifier
                        .size(width = 74.84.dp, height = 74.84.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(color = GreenPastel),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.settings_vector_bar),
                        contentDescription = "Home",
                        modifier = Modifier
                            .size(width = 36.2.dp, height = 39.08.dp)
                            .offset(x = 0.dp, y = (2).dp)
                            .clip(RoundedCornerShape(10.dp))
                            .blur(3.dp),
                        colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.25f))
                    )
                    Image(
                        painter = painterResource(R.drawable.settings_vector_bar),
                        contentDescription = "Deskripsi aksesibilitas",
                        modifier = Modifier
                            .size(width = 34.2.dp, height = 37.08.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        colorFilter = ColorFilter.tint(BlueDonker)
                    )
                }
            }
        }
    }
}

@Composable
fun BoxWithShadow(
    modifier: Modifier = Modifier,
    boxWidth: Dp = 160.dp,
    boxHeight: Dp = 90.dp,
    cornerRadius: Dp = 5.dp,
    shadowColor: Color = Color.Black.copy(alpha = 0.25f),
    shadowBlurRadius: Dp = 80.dp,
    shadowOffsetX: Dp = 1.dp,
    shadowOffsetY: Dp = 0.dp,
    boxColor: Color = DarkGray,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable () -> Unit = {}
) {
    Box(modifier) {
        // Shadow layer
        Box(
            modifier = Modifier
                .padding(vertical = 3.dp)
                .clip(RoundedCornerShape(cornerRadius))
                .blur(radius = shadowBlurRadius)
                .wrapContentSize()
        ) {
            Box(
                modifier = Modifier
                    .size(width = boxWidth + 2.dp, height = boxHeight)
                    .clip(RoundedCornerShape(cornerRadius))
                    .background(color = shadowColor)
            )
        }

        // Main box with content
        Box(
            modifier = Modifier
                .size(width = boxWidth, height = boxHeight)
                .offset(x = shadowOffsetX, y = shadowOffsetY)
                .clip(RoundedCornerShape(cornerRadius))
                .background(color = boxColor),
            contentAlignment = contentAlignment
        ) {
            content()
        }

    }
}

@Composable
fun RightAnalog(
    modifier: Modifier = Modifier,
    stickSize: Dp = 60.dp, // Besar stick, set deafult 60 dp dalam diameter
    padSize: Dp = 150.dp, // Besar pad atau lingkaran batas Stick dalam diameter
    viewModel: RightAnalogHandler = viewModel() // Untuk mengirimkan event dari stickPosition ke activity
) {
    // State untuk posisi stick
    val stickPosition by viewModel.stickPosition.collectAsState() // Ini merupakan titik tengah Stick, diset 0 agar berada di tengah
    val maxDistance = (padSize - stickSize) / 2 // Jarak maksimal stickPosition pada kanvas, variabel ini digunakan agar stick tidak keluar pad

    Box(modifier = modifier.size(padSize)) {
        Canvas(
            modifier = Modifier
                .size(padSize)
        ){
            val gradientBrushPad = Brush.radialGradient(
                colorStops = arrayOf(
                    0.8f to BlueDonker,
                    1.0f to Color.Black.copy(alpha = 0.25f)
                ),
                radius = size.minDimension / 1.8f
            )

            drawCircle(
                brush = gradientBrushPad,
                radius = size.minDimension / 2,
            )
        }

        Canvas(
            modifier = Modifier
                .size(padSize)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            change.consume()
                            viewModel.updateStickPosition(dragAmount, maxDistance.toPx())
                        },
                        onDragEnd = {
                            viewModel.resetStick()
                        }
                    )
                }
        ) {

            // Shadow Stick
            val gradientBrushShadow = Brush.radialGradient(
                colorStops = arrayOf(
                    0.2f to Color.Black,
                    1.0f to BlueDonker.copy(alpha = 0.5f)
                ),
                center = center + stickPosition,
                radius = stickSize.toPx() / 1.6f
            )

            drawCircle(
                brush = gradientBrushShadow,
                radius = stickSize.toPx() / 1.5f,
                center = center + stickPosition
            )

            // Main Stick
            val gradientBrush = Brush.radialGradient(
                colorStops = arrayOf(
                    0.2f to Color.White,
                    1.0f to Color.Black.copy(alpha = 0.15f)
                ),
                center = center + stickPosition,
                radius = stickSize.toPx() / 2
            )

            drawCircle(
                brush = gradientBrush,
                radius = stickSize.toPx() / 2,
                center = center + stickPosition,
            )
        }
    }
}

@Composable
fun LeftDpadView(
    padSize: Dp = 150.dp,
    viewModel: LeftDPad = viewModel(),
    maxStep: Int = 13000,
    serverHandler: ServerHandler = viewModel()
) {

    Box(
        modifier = Modifier.size(padSize),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(padSize)) {
            val gradientBrushPad = Brush.radialGradient(
                colorStops = arrayOf(
                    0.8f to BlueDonker,
                    1.0f to Color.Black.copy(alpha = 0.25f)
                ),
                radius = size.minDimension / 1.8f
            )
            drawCircle(
                brush = gradientBrushPad,
                radius = size.minDimension / 2,
            )
        }

        Column {
            Image(
                painter = painterResource(R.drawable.arrow_vector_leftjoystick),
                contentDescription = "LeftAnalogUp",
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                Log.d("DPAD", "Tombol atas ditekan")
                                viewModel.startHolding(StepperDirection.UP, maxStep) { newStep ->
                                    serverHandler.emitHandler("leftdpad_step", newStep)
                                }
                                tryAwaitRelease()
                                viewModel.stopHolding()
                            }
                        )
                    }
            )

            Spacer(Modifier.height(50.dp))

            Image(
                painter = painterResource(R.drawable.arrow_vector_leftjoystick),
                contentDescription = "LeftAnalogBot",
                modifier = Modifier
                    .rotate(180f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                Log.d("DPAD", "Tombol bawah ditekan")
                                viewModel.startHolding(StepperDirection.DOWN, maxStep) { newStep ->
                                    serverHandler.emitHandler("leftdpad_step", newStep)
                                }
                                tryAwaitRelease()
                                viewModel.stopHolding()
                            }
                        )
                    }
            )
        }
    }
}


@Preview
@Composable
fun LeftAnalogView(){
    LeftDpadView()
}

@Preview
@Composable
fun JoystickScreen() {
    var xValue by remember { mutableFloatStateOf(0f) }
    var yValue by remember { mutableFloatStateOf(0f) }

    RightAnalog()

}
