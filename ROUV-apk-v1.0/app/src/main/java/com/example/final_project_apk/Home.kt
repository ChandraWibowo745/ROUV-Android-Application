package com.example.final_project_apk


import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import com.example.final_project_apk.ui.theme.ArchivoBlackTypography
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.final_project_apk.layout.BoxWithShadow
import com.example.final_project_apk.ui.theme.ArchivoTypography
import com.example.final_project_apk.ui.theme.BlueDonker
import com.example.final_project_apk.ui.theme.GreenPastel
import com.example.final_project_apk.ui.theme.White
import com.example.final_project_apk.ui.theme.RedPastel
import com.example.final_project_apk.network.status.ServerHandler
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.final_project_apk.network.status.UpdateStatus


@Composable
fun Home_page(viewModel : ServerHandler = viewModel()){

    val detectedCount by viewModel.detectedCount.collectAsState()
    val state by viewModel.updateStatus.collectAsState()
    val androidText: String
    val movementText: String
    val ballastText: String
    val lastUpdatedText: String

    when (val currentState = state) {
        is UpdateStatus.Loading -> {
            androidText = "Connecting"
            movementText = "Connecting"
            ballastText = "Connecting"
            lastUpdatedText = "Updating..."
        }

        is UpdateStatus.Success -> {
            androidText = currentState.android
            movementText = currentState.movement
            ballastText = currentState.ballast
            lastUpdatedText = currentState.lastUpdated
        }
    }

    val statusColor = when (state) {
        is UpdateStatus.Loading -> RedPastel
        is UpdateStatus.Success -> GreenPastel
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BlueDonker
    ) {
        Column(modifier = Modifier.fillMaxWidth())
        {
            Box(modifier = Modifier.fillMaxWidth()
                .height(400.dp)) {

                Image(
                    painter = painterResource(R.drawable.big_ellipse_clip_content),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.TopStart,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(374.dp)
                        .clip(RectangleShape)
                        .offset(x = (-1).dp, y = (4).dp)
                        .blur(4.dp),
                    colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.25f))
                )
                Image(
                    painter = painterResource(R.drawable.big_ellipse_clip_content),
                    contentDescription = "Deskripsi aksesibilitas",
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.TopStart,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(374.dp)
                        .clip(RectangleShape)
                )
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .padding(
                                horizontal = 20.dp,
                                vertical = 18.dp
                            )
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "HOME",
                            style = ArchivoBlackTypography.bodyLarge.copy(
                                shadow = Shadow(
                                    color = Color.Black.copy(alpha = 0.25f),
                                    offset = Offset(0f, 10f),
                                    blurRadius = 4f
                                )
                            ),
                            color = White,
                            fontSize = 30.sp
                        )
                        Spacer(modifier = Modifier.width(194.dp).height(1.dp)) // Jarak 16dp
                        Image(
                            painter = painterResource(R.drawable.logo_blue),
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp)
                                .offset(y = 4.dp)
                        )

                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState()) // Tambahkan scroll horizontal
                            .padding(horizontal = 20.dp
                                , vertical = 25.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BoxWithShadow()

                        Spacer(modifier = Modifier.width(20.dp))

                        BoxWithShadow()

                        Spacer(modifier = Modifier.width(20.dp))

                        BoxWithShadow()
                    }

                }
            }

            Row(
                modifier = Modifier
                    .padding(
                        horizontal = 20.dp,
                        vertical = 18.dp
                    )
                    .wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Raspberry Pi Status :",
                    style = ArchivoBlackTypography.bodyLarge.copy(
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.25f),
                            offset = Offset(0f, 10f),
                            blurRadius = 4f
                        )
                    ),
                    color = White,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .wrapContentSize()
                )
                Spacer(modifier = Modifier.width(20.dp))
                BoxWithShadow(modifier = Modifier
                    .wrapContentSize(),
                    cornerRadius = 20.dp,
                    boxWidth = 100.dp,
                    boxHeight = 28.dp,
                    boxColor = statusColor,
                    shadowBlurRadius = 7.dp
                    ){
                    Text(
                            text = androidText,
                            style = ArchivoTypography.bodyLarge.copy(
                                shadow = Shadow(
                                    color = Color.Black.copy(alpha = 0.25f),
                                    offset = Offset(0f, 10f),
                                    blurRadius = 4f
                                )
                            ),
                            color = White,
                            fontSize = 13.sp,
                            modifier = Modifier
                                .wrapContentSize()
                        )
                }
            }
            Row(
                modifier = Modifier
                    .padding(
                        horizontal = 20.dp,
                        vertical = 18.dp
                    )
                    .wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Movement Status       :",
                    style = ArchivoBlackTypography.bodyLarge.copy(
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.25f),
                            offset = Offset(0f, 10f),
                            blurRadius = 4f
                        )
                    ),
                    color = White,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .wrapContentSize()
                )
                Spacer(modifier = Modifier.width(20.dp))
                BoxWithShadow(modifier = Modifier
                    .wrapContentSize(),
                    cornerRadius = 20.dp,
                    boxWidth = 100.dp,
                    boxHeight = 28.dp,
                    boxColor = if (movementText == "Disconnected"){
                        RedPastel} else{statusColor},
                    shadowBlurRadius = 7.dp
                ){
                    Text(
                        text = movementText,
                        style = ArchivoTypography.bodyLarge.copy(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.25f),
                                offset = Offset(0f, 10f),
                                blurRadius = 4f
                            )
                        ),
                        color = White,
                        fontSize = 13.sp,
                        modifier = Modifier
                            .wrapContentSize()
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(
                        horizontal = 20.dp,
                        vertical = 18.dp
                    )
                    .wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Ballast Status                  :",
                    style = ArchivoBlackTypography.bodyLarge.copy(
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.25f),
                            offset = Offset(0f, 10f),
                            blurRadius = 4f
                        )
                    ),
                    color = White,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .wrapContentSize()
                )
                Spacer(modifier = Modifier.width(20.dp))
                BoxWithShadow(modifier = Modifier
                    .wrapContentSize(),
                    cornerRadius = 20.dp,
                    boxWidth = 100.dp,
                    boxHeight = 28.dp,
                    boxColor = if (ballastText == "Disconnected"){
                        RedPastel} else{statusColor},
                    shadowBlurRadius = 7.dp
                ){
                    Text(
                        text = ballastText,
                        style = ArchivoTypography.bodyLarge.copy(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.25f),
                                offset = Offset(0f, 10f),
                                blurRadius = 4f
                            )
                        ),
                        color = White,
                        fontSize = 13.sp,
                        modifier = Modifier
                            .wrapContentSize()
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(
                        horizontal = 20.dp,
                        vertical = 18.dp
                    )
                    .wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Last Updated                    :",
                    style = ArchivoBlackTypography.bodyLarge.copy(
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.25f),
                            offset = Offset(0f, 10f),
                            blurRadius = 4f
                        )
                    ),
                    color = White,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .wrapContentSize()
                )
                Spacer(modifier = Modifier.width(20.dp))
                BoxWithShadow(modifier = Modifier
                    .wrapContentSize(),
                    cornerRadius = 20.dp,
                    boxWidth = 100.dp,
                    boxHeight = 28.dp,
                    boxColor = statusColor,
                    shadowBlurRadius = 7.dp
                ){
                    Text(
                        text = lastUpdatedText,
                        style = ArchivoTypography.bodyLarge.copy(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.25f),
                                offset = Offset(0f, 10f),
                                blurRadius = 4f
                            )
                        ),
                        color = White,
                        fontSize = 13.sp,
                        modifier = Modifier
                            .wrapContentSize()
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(
                        horizontal = 20.dp,
                        vertical = 18.dp
                    )
                    .wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Detected                                 :",
                    style = ArchivoBlackTypography.bodyLarge.copy(
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.25f),
                            offset = Offset(0f, 10f),
                            blurRadius = 4f
                        )
                    ),
                    color = White,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .wrapContentSize()
                )
                Spacer(modifier = Modifier.width(20.dp))
                BoxWithShadow(modifier = Modifier
                    .wrapContentSize(),
                    cornerRadius = 20.dp,
                    boxWidth = 100.dp,
                    boxHeight = 28.dp,
                    boxColor = GreenPastel,
                    shadowBlurRadius = 7.dp
                ){
                    Text(
                        text = detectedCount.toString(),
                        style = ArchivoTypography.bodyLarge.copy(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.25f),
                                offset = Offset(0f, 10f),
                                blurRadius = 4f
                            )
                        ),
                        color = White,
                        fontSize = 13.sp,
                        modifier = Modifier
                            .wrapContentSize()
                    )
                }
            }
        }
    }
}



