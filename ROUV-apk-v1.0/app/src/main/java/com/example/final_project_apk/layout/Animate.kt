package com.example.final_project_apk.layout


import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.final_project_apk.Controller_page
import com.example.final_project_apk.Home_page
import com.example.final_project_apk.MainActivity
import com.example.final_project_apk.R
import com.example.final_project_apk.Settings_page
import com.example.final_project_apk.network.status.ServerHandler
import com.example.final_project_apk.ui.theme.BlueDonker
import com.example.final_project_apk.ui.theme.GreenPastel

enum class Tab { HOME, JOYSTICK, SETTINGS }

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun Assemble(
    serverHandler : ServerHandler = viewModel(),
    isSelectedTab : Tab = Tab.HOME
) {
    // State Status Tab
    val context = LocalContext.current
    val activity = context as? MainActivity
    var selectedTab by remember { mutableStateOf(isSelectedTab) }

    // Ganti orientasi saat tab berubah
    LaunchedEffect(selectedTab) {
        when (selectedTab) {
            Tab.JOYSTICK -> activity?.setOrientationLandscape()
            Tab.HOME, Tab.SETTINGS -> activity?.setOrientationPortrait()
        }
    }

    when (selectedTab) {
        Tab.HOME -> Home_page(serverHandler)
        Tab.SETTINGS -> Settings_page(serverHandler = serverHandler)
        Tab.JOYSTICK -> Controller_page(serverHandler = serverHandler)
    }

    Box( modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.BottomStart) {



        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(132.dp),
            contentAlignment = Alignment.BottomCenter
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

                        val colorShadowHome by animateColorAsState(
                            targetValue = when (selectedTab) {
                                Tab.HOME -> GreenPastel
                                else -> Color.Black.copy(alpha = 0.25f)
                            },
                            label = "Home_Disappear"
                        )
                        val colorHome by animateColorAsState(
                            targetValue = when (selectedTab) {
                                Tab.HOME -> GreenPastel
                                else -> White
                            },
                            label = "Home_Main_Disappear"
                        )

                        Image(
                            painter = painterResource(R.drawable.home_vector_bar),
                            contentDescription = "shadowHome",
                            modifier = Modifier
                                .size(width = 36.2.dp, height = 39.08.dp)
                                .offset(x = (-1).dp, y = (2).dp)
                                .clip(RoundedCornerShape(5.dp))
                                .blur(4.dp),
                            colorFilter = ColorFilter.tint(colorShadowHome)
                        )
                        Image(
                            painter = painterResource(R.drawable.home_vector_bar),
                            contentDescription = "home",
                            modifier = Modifier
                                .size(width = 34.2.dp, height = 37.08.dp)
                                .clip(RectangleShape)
                                .clickable {
                                    selectedTab = Tab.HOME
                                },
                            colorFilter = ColorFilter.tint(colorHome)
                        )
                    }

                    Spacer(Modifier.width(100.dp))

                    Box {
                        val colorShadowJoystick by animateColorAsState(
                            targetValue = when (selectedTab) {
                                Tab.JOYSTICK -> GreenPastel
                                else -> Color.Black.copy(alpha = 0.25f)
                            },
                            label = "joystick_Disappear"
                        )
                        val colorJoystick by animateColorAsState(
                            targetValue = when (selectedTab) {
                                Tab.JOYSTICK -> GreenPastel
                                else -> White
                            },
                            label = "joystick_Main_Disappear"
                        )

                        Image(
                            painter = painterResource(R.drawable.joystick_vector_bar),
                            contentDescription = "shadowJoystick",
                            modifier = Modifier
                                .size(width = 36.2.dp, height = 39.08.dp)
                                .offset(x = (-1).dp, y = (2).dp)
                                .clip(RoundedCornerShape(10.dp))
                                .blur(4.dp),
                            colorFilter = ColorFilter.tint(colorShadowJoystick)
                        )
                        Image(
                            painter = painterResource(R.drawable.joystick_vector_bar),
                            contentDescription = "joystick",
                            modifier = Modifier
                                .size(width = 34.2.dp, height = 37.08.dp)
                                .clip(RectangleShape)
                                .clickable {
                                    selectedTab = Tab.JOYSTICK
                                },
                            colorFilter = ColorFilter.tint(colorJoystick)
                        )
                    }

                    Spacer(Modifier.width(100.dp))

                    Box {
                        val colorShadowSettings by animateColorAsState(
                            targetValue = when (selectedTab) {
                                Tab.SETTINGS -> GreenPastel
                                else -> Color.Black.copy(alpha = 0.25f)
                            },
                            label = "Settings_Disappear"
                        )
                        val colorSettings by animateColorAsState(
                            targetValue = when (selectedTab) {
                                Tab.SETTINGS -> GreenPastel
                                else -> White
                            },
                            label = "Settings_Main_Disappear"
                        )
                        Image(
                            painter = painterResource(R.drawable.settings_vector_bar),
                            contentDescription = "shadowSettings",
                            modifier = Modifier
                                .size(width = 36.2.dp, height = 39.08.dp)
                                .offset(x = (-1).dp, y = (2).dp)
                                .clip(RoundedCornerShape(10.dp))
                                .blur(4.dp),
                            colorFilter = ColorFilter.tint(colorShadowSettings)
                        )
                        Image(
                            painter = painterResource(R.drawable.settings_vector_bar),
                            contentDescription = "settings",
                            modifier = Modifier
                                .size(width = 34.2.dp, height = 37.08.dp)
                                .clip(RectangleShape)
                                .clickable {
                                    selectedTab = Tab.SETTINGS
                                },
                            colorFilter = ColorFilter.tint(colorSettings)
                        )
                    }
                }
            }

            val movingElipse by animateDpAsState(
                targetValue = when (selectedTab) {
                    Tab.HOME -> (-130).dp
                    Tab.JOYSTICK -> 0.dp
                    Tab.SETTINGS -> 130.dp
                },
                label = "Settings_Disappear"
            )

            val selectedDrawable = when (selectedTab) {
                Tab.HOME -> R.drawable.home_vector_bar
                Tab.JOYSTICK -> R.drawable.joystick_vector_bar
                Tab.SETTINGS -> R.drawable.settings_vector_bar
            }

            Row {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .offset(x = movingElipse, y = (-19).dp),
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
                            AnimatedContent(targetState = selectedDrawable, label = "") { drawable ->
                                Image(
                                    painter = painterResource(id = drawable),
                                    contentDescription = "Home",
                                    modifier = Modifier
                                        .size(width = 36.2.dp, height = 39.08.dp)
                                        .offset(x = 0.dp, y = (2).dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .blur(3.dp),
                                    colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.25f))
                                )
                                Image(
                                    painter = painterResource(id = drawable),
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
        }
    }
}

@Preview
@Composable
fun AssembleView(){
    Assemble()
}


