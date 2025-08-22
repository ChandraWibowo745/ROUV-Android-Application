package com.example.final_project_apk


import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.final_project_apk.layout.BoxWithShadow
import com.example.final_project_apk.layout.FullScreenMode
import com.example.final_project_apk.layout.LeftDpadView
import com.example.final_project_apk.layout.RightAnalog
import com.example.final_project_apk.layout.findActivity
import com.example.final_project_apk.network.control.leftdpad.LeftDPad
import com.example.final_project_apk.network.control.rightanalog.RightAnalogHandler
import com.example.final_project_apk.network.status.ServerHandler
import com.example.final_project_apk.ui.theme.BlueDonker
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.final_project_apk.network.PreferenceManager
import com.example.final_project_apk.ui.theme.ArchivoBlackTypography
import com.example.final_project_apk.ui.theme.ArchivoTypography
import com.example.final_project_apk.ui.theme.GreenPastel
import com.example.final_project_apk.ui.theme.White


@Preview(widthDp = 900,
    heightDp = 400,
    showBackground = true)
@Composable
fun Controller_page(
    rightanalogViewModel: RightAnalogHandler = viewModel(),
    leftDPad: LeftDPad = viewModel(),
    serverHandler: ServerHandler = viewModel()
){
    FullScreenMode()
    SetNavigationBarColor(color = BlueDonker)

    val normalizedPosition by rightanalogViewModel.normalizedPosition.collectAsState()
    val stepIndexDpad by leftDPad.stepIndex.collectAsState()
    val maxStep by serverHandler.maxStep.collectAsState()
    var reloadTrigger by remember { mutableIntStateOf(0) }
    val pwmValue by serverHandler.pwmValue.collectAsState()
    val currentStep by serverHandler.currentStep.collectAsState()
    val fps by serverHandler.fpsState.collectAsState()
    val context = LocalContext.current
    val (activeIp, _, activeStreamPort) = PreferenceManager.getActiveIpEntry(context)
        ?: Triple("192.168.1.99", "5000", "8000") // fallback default kalau belum ada yang disimpan

    val videoUrl = "http://$activeIp:$activeStreamPort/video_feed"

    // Kirim ke server setiap kali posisi joystick berubah
    LaunchedEffect(normalizedPosition) {
        val timestampAndroid = System.currentTimeMillis()
        serverHandler.emitHandler(
            "rightanalog_move", // Nama event yang di-handle server
            normalizedPosition.x,
            normalizedPosition.y,
            timestampAndroid
        )
    }

    LaunchedEffect(stepIndexDpad) {
        val timestamp = System.currentTimeMillis()
        Log.d("DPad", "Step index updated: $stepIndexDpad")
        serverHandler.emitHandler("leftdpad_step", stepIndexDpad, timestamp)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BlueDonker
    ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.wrapContentSize()) {
        Row(modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()){
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.wrapContentSize()) {
                Row(
                    modifier = Modifier
                        .padding(
                            horizontal = 20.dp,
                            vertical = 5.dp
                        )
                        .wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "FPS                      :",
                        style = ArchivoBlackTypography.bodyLarge.copy(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.25f),
                                offset = Offset(0f, 10f),
                                blurRadius = 4f
                            )
                        ),
                        color = White,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .wrapContentSize()
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    BoxWithShadow(
                        modifier = Modifier
                            .wrapContentSize(),
                        cornerRadius = 20.dp,
                        boxWidth = 50.dp,
                        boxHeight = 28.dp,
                        boxColor = GreenPastel,
                        shadowBlurRadius = 7.dp
                    ) {
                        Text(
                            text = fps.toString(),
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
                            vertical = 5.dp
                        )
                        .wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "MAX STEP  : ",
                        style = ArchivoBlackTypography.bodyLarge.copy(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.25f),
                                offset = Offset(0f, 10f),
                                blurRadius = 4f
                            )
                        ),
                        color = White,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .wrapContentSize()
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    BoxWithShadow(
                        modifier = Modifier
                            .wrapContentSize(),
                        cornerRadius = 20.dp,
                        boxWidth = 50.dp,
                        boxHeight = 28.dp,
                        boxColor = GreenPastel,
                        shadowBlurRadius = 7.dp
                    ) {
                        Text(
                            text = maxStep.toString(),
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
                            vertical = 5.dp
                        )
                        .wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "STEP                  :",
                        style = ArchivoBlackTypography.bodyLarge.copy(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.25f),
                                offset = Offset(0f, 10f),
                                blurRadius = 4f
                            )
                        ),
                        color = White,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .wrapContentSize()
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    BoxWithShadow(
                        modifier = Modifier
                            .wrapContentSize(),
                        cornerRadius = 20.dp,
                        boxWidth = 50.dp,
                        boxHeight = 28.dp,
                        boxColor = GreenPastel,
                        shadowBlurRadius = 7.dp
                    ) {
                        Text(
                            text = currentStep.toString(),
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

            Spacer(modifier = Modifier.width(5.dp))

            BoxWithShadow(boxWidth = 422.dp, boxHeight = 222.dp, contentAlignment = Alignment.BottomEnd)
                {
                    BoxWithConstraints(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val maxW = constraints.maxWidth
                        val calculatedHeight = (maxW * (452f / 805f)).toInt() // aspect ratio sesuai stream

                        key(reloadTrigger) {
                            AndroidView(
                                factory = { context ->
                                    WebView(context).apply {
                                        settings.javaScriptEnabled = false
                                        webViewClient = WebViewClient()
                                        loadUrl(videoUrl)
                                    }
                                },
                                modifier = Modifier
                                    .width(with(LocalDensity.current) { maxW.toDp() })
                                    .height(with(LocalDensity.current) { calculatedHeight.toDp() })
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    BoxWithShadow(
                        Modifier.clickable { reloadTrigger++ }, boxWidth = 30.dp, boxHeight = 30.dp, boxColor = White, cornerRadius = 100.dp
                    ){
                        Icon(Icons.Default.Refresh, contentDescription = "Reconnect")
                    }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.wrapContentSize()) {

                Text(
                    text = "Coordinate   :",
                    style = ArchivoBlackTypography.bodyLarge.copy(
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.25f),
                            offset = Offset(0f, 10f),
                            blurRadius = 4f
                        )
                    ),
                    color = White,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .wrapContentSize()
                )

                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    modifier = Modifier
                        .padding(
                            horizontal = 20.dp,
                            vertical = 5.dp
                        )
                        .wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "X   :",
                        style = ArchivoBlackTypography.bodyLarge.copy(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.25f),
                                offset = Offset(0f, 10f),
                                blurRadius = 4f
                            )
                        ),
                        color = White,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .wrapContentSize()
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    BoxWithShadow(
                        modifier = Modifier
                            .wrapContentSize(),
                        cornerRadius = 20.dp,
                        boxWidth = 50.dp,
                        boxHeight = 28.dp,
                        boxColor = GreenPastel,
                        shadowBlurRadius = 7.dp
                    ) {
                        Text(
                            text = normalizedPosition.x.toString(),
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
                            vertical = 5.dp
                        )
                        .wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Y   :",
                        style = ArchivoBlackTypography.bodyLarge.copy(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.25f),
                                offset = Offset(0f, 10f),
                                blurRadius = 4f
                            )
                        ),
                        color = White,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .wrapContentSize()
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    BoxWithShadow(
                        modifier = Modifier
                            .wrapContentSize(),
                        cornerRadius = 20.dp,
                        boxWidth = 50.dp,
                        boxHeight = 28.dp,
                        boxColor = GreenPastel,
                        shadowBlurRadius = 7.dp
                    ) {
                        Text(
                            text = normalizedPosition.y.toString(),
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
                            vertical = 5.dp
                        )
                        .wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "PWM :",
                        style = ArchivoBlackTypography.bodyLarge.copy(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.25f),
                                offset = Offset(0f, 10f),
                                blurRadius = 4f
                            )
                        ),
                        color = White,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .wrapContentSize()
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    BoxWithShadow(
                        modifier = Modifier
                            .wrapContentSize(),
                        cornerRadius = 20.dp,
                        boxWidth = 50.dp,
                        boxHeight = 28.dp,
                        boxColor = GreenPastel,
                        shadowBlurRadius = 7.dp
                    ) {
                        Text(
                            text = pwmValue.toString(), // Gunakan state pwmValue dari SharedPreferences
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

        Row(modifier = Modifier
            .offset(y = (-80).dp)) {
            LeftDpadView(
                padSize = 150.dp,
                maxStep = maxStep,
                serverHandler = serverHandler // Tambahkan ini
            )

            Spacer(Modifier.width(500.dp))

                RightAnalog(
                    padSize = 150.dp,
                    viewModel = rightanalogViewModel
                )
            }
        }
    }
}


// Opsional: Atur warna navigation bar
@Composable
fun SetNavigationBarColor(color: Color) {
    val context = LocalContext.current
    val activity = context.findActivity() ?: return

    DisposableEffect(color) {
        val window = activity.window
        window.navigationBarColor = color.toArgb()

        onDispose {
            // Kembalikan ke warna default jika perlu
            window.navigationBarColor = Color.Transparent.toArgb()
        }
    }
}









