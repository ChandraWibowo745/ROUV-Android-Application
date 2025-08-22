package com.example.final_project_apk

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.final_project_apk.network.PreferenceManager
import com.example.final_project_apk.network.SocketHandler
import com.example.final_project_apk.network.status.ServerHandler
import com.example.final_project_apk.ui.theme.BlueDonker
import com.example.final_project_apk.ui.theme.GreenPastel
import com.example.final_project_apk.ui.theme.RedPastel
import com.example.final_project_apk.ui.theme.White

@Preview(showBackground = true)
@Composable
fun Settings_page(
    serverHandler: ServerHandler = viewModel()
){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BlueDonker
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.wrapContentSize()) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(284.dp)
                        .clip(RectangleShape)
                ) {
                    Image(
                        painter = painterResource(R.drawable.big_ellipse_clip_content),
                        contentScale = ContentScale.FillWidth,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomStart)
                            .blur(4.dp),
                        colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.25f))

                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .clip(RectangleShape)
                ) {
                    Image(
                        painter = painterResource(R.drawable.big_ellipse_clip_content),
                        contentDescription = "Deskripsi aksesibilitas",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomStart) // Paksa gambar ke bawah
                    )
                }
                Column(modifier = Modifier.fillMaxWidth().wrapContentSize()) {
                    Row(
                        modifier = Modifier
                            .padding(
                                horizontal = 20.dp,
                                vertical = 20.dp
                            )
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "SETTINGS",
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
                        Spacer(modifier = Modifier.width(124.dp).height(1.dp)) // Jarak 16dp
                        Image(
                            painter = painterResource(R.drawable.logo_blue),
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp)
                                .offset(y = 4.dp)
                        )

                    }
                }
            }

            val context = LocalContext.current
            val ipList = remember { mutableStateListOf<Triple<String, String, String>>() }
            var ip by remember { mutableStateOf("") }
            var socketPort by remember { mutableStateOf("") }
            var streamPort by remember { mutableStateOf("") }
            var activeIndex by remember { mutableIntStateOf(PreferenceManager.getActiveIndex(context)) }
            var maxStepText by remember { mutableStateOf("") }
            val maxStepSaved = remember { mutableStateOf("") }
            var pwmText by remember { mutableStateOf("") }
            val pwmSaved = remember { mutableStateOf("") }

            // Load nilai Max Step dari SharedPreferences
            LaunchedEffect(Unit) {
                val savedMaxStep = PreferenceManager.getMaxStep(context)
                maxStepText = savedMaxStep.toString()
                maxStepSaved.value = savedMaxStep.toString()
            }

            // Load IP list saat pertama kali
            LaunchedEffect(Unit) {
                ipList.clear()
                ipList.addAll(PreferenceManager.getIpList(context))
            }

            val scrollState = rememberScrollState()

            Column(modifier = Modifier.fillMaxWidth().offset(y = ((-100).dp)).height(490.dp).verticalScroll(scrollState)) {
                Text(
                    "Add New IP", style = ArchivoBlackTypography.bodyLarge.copy(
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.25f),
                            offset = Offset(0f, 10f),
                            blurRadius = 4f
                        )
                    ), color = White, fontSize = 18.sp, modifier = Modifier.padding(start = 20.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                val customColors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GreenPastel,
                    unfocusedBorderColor = GreenPastel.copy(alpha = 0.6f),
                    cursorColor = GreenPastel,
                    focusedLabelColor = GreenPastel,
                    unfocusedLabelColor = GreenPastel.copy(alpha = 0.7f),
                    disabledTextColor = White.copy(alpha = 0.4f),
                    focusedTextColor = White,
                    unfocusedTextColor = White,
                    disabledBorderColor = Color.Gray,
                    disabledLabelColor = Color.Gray,
                    disabledSupportingTextColor = Color.Gray,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                )

                Row(modifier = Modifier.padding(horizontal = 20.dp)) {
                    OutlinedTextField(
                        value = ip,
                        onValueChange = { ip = it },
                        label = { Text("Internet Protocol", color = White) },
                        colors = customColors,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = socketPort,
                        onValueChange = { socketPort = it },
                        label = { Text("Socket Port", color = White) },
                        colors = customColors,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = streamPort,
                        onValueChange = { streamPort = it },
                        label = { Text("Stream Port", color = White) },
                        colors = customColors,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (ip.isNotBlank() && socketPort.isNotBlank() && streamPort.isNotBlank()) {
                            PreferenceManager.saveIpEntry(context, ip, socketPort, streamPort)
                            ipList.clear()
                            ipList.addAll(PreferenceManager.getIpList(context))
                            activeIndex = PreferenceManager.getActiveIndex(context)
                            ip = ""
                            socketPort = ""
                            streamPort = ""
                            SocketHandler.initSocket(context)
                        }
                    },
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GreenPastel,
                        contentColor = White
                    )
                ) {
                    Text("Save and Use This IP")
                }


                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "Select the IP you want to use:",
                    color = White,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 20.dp)
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 240.dp) // Atur tinggi maksimal scroll
                        .padding(horizontal = 20.dp)
                ) {
                    itemsIndexed(ipList) { index, (savedIp, savedSocketPort, savedStreamPort) ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        ) {
                            RadioButton(
                                selected = index == activeIndex,
                                onClick = {
                                    PreferenceManager.setActiveIndex(context, index)
                                    activeIndex = index
                                    SocketHandler.initSocket(context)
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = GreenPastel,
                                    unselectedColor = RedPastel
                                )
                            )
                            Text(
                                text = "$savedIp:$savedSocketPort (Stream: $savedStreamPort)",
                                color = White,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 8.dp)
                            )
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Hapus IP",
                                tint = Color.Red,
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
                                        PreferenceManager.removeIpEntry(context, index)
                                        ipList.clear()
                                        ipList.addAll(PreferenceManager.getIpList(context))
                                        activeIndex = PreferenceManager.getActiveIndex(context)
                                    }
                            )
                        }
                        Divider(color = Color.White.copy(alpha = 0.2f))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Max Step Ballast", style = ArchivoBlackTypography.bodyLarge.copy(
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.25f),
                            offset = Offset(0f, 10f),
                            blurRadius = 4f
                        )
                    ), color = White, fontSize = 18.sp, modifier = Modifier.padding(start = 20.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.padding(horizontal = 20.dp)) {
                    OutlinedTextField(
                        value = maxStepText,
                        onValueChange = {
                            if (it.all { char -> char.isDigit() }) {
                                maxStepText = it
                            }
                        },
                        label = { Text("Example: 1000", color = White) },
                        colors = customColors,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            if (maxStepText.isNotBlank()) {
                                val newStep = maxStepText.toInt()
                                PreferenceManager.setMaxStep(context, newStep) // tetap simpan lokal
                                serverHandler.updateMaxStepFromSettings(newStep) // kirim ke server + update controller
                                maxStepSaved.value = maxStepText
                            }
                        },
                        modifier = Modifier.align(Alignment.CenterVertically),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GreenPastel,
                            contentColor = White
                        )
                    ) {
                        Text("Save")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "PWM Value", style = ArchivoBlackTypography.bodyLarge.copy(
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.25f),
                            offset = Offset(0f, 10f),
                            blurRadius = 4f
                        )
                    ), color = White, fontSize = 18.sp, modifier = Modifier.padding(start = 20.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.padding(horizontal = 20.dp)) {
                    OutlinedTextField(
                        value = pwmText,
                        onValueChange = {
                            if (it.all { char -> char.isDigit() }) {
                                pwmText = it
                            }
                        },
                        label = { Text("Example: 10, max = 100", color = White) },
                        colors = customColors,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            if (pwmText.isNotBlank()) {
                                val newPwm = pwmText.toInt()
                                PreferenceManager.setPwmValue(context, newPwm)  // Simpan lokal
                                serverHandler.updatePwmFromSettings(newPwm)    // Kirim ke Flask server
                                pwmSaved.value = pwmText
                            }
                        },
                        modifier = Modifier.align(Alignment.CenterVertically),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GreenPastel,
                            contentColor = White
                        )
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}