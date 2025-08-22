package com.example.final_project_apk

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.final_project_apk.layout.Assemble
import com.example.final_project_apk.network.status.ServerHandler

class MainActivity : ComponentActivity() {

    fun setOrientationLandscape() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    fun setOrientationPortrait() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    fun setOrientationAuto() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val serverHandler: ServerHandler = viewModel()

            LaunchedEffect(Unit) {
                serverHandler.connect()
            }

            Assemble(serverHandler = serverHandler)
        }
    }
}