package com.example.final_project_apk.network

import android.app.Application
import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetectionViewModel(application: Application) : AndroidViewModel(application) {
    private val _latestImage = MutableStateFlow<ByteArray?>(null)
    val latestImage: StateFlow<ByteArray?> = _latestImage

    private val _pollingActive = MutableStateFlow(false)

    fun startPolling(serverUrl: String) {
        val appContext = getApplication<Application>().applicationContext

        if (_pollingActive.value) return  // Jangan polling dua kali
        _pollingActive.value = true

        Log.d("DetectionVM", "Start polling: $serverUrl")

        viewModelScope.launch {
            var lastImageBytes: ByteArray? = null

            while (_pollingActive.value) {
                try {
                    val bytes = withContext(Dispatchers.IO) {
                        val url = URL(serverUrl)
                        val connection = url.openConnection() as HttpURLConnection
                        connection.connectTimeout = 2000
                        connection.readTimeout = 2000

                        val result = if (connection.responseCode == 200) {
                            connection.inputStream.readBytes()
                        } else {
                            Log.w("DetectionVM", "Server response: ${connection.responseCode}")
                            null
                        }
                        connection.disconnect()
                        result
                    }

                    if (bytes != null && !bytes.contentEquals(lastImageBytes)) {
                        lastImageBytes = bytes
                        _latestImage.value = bytes
                        Log.d("DetectionVM", "New image detected and updated")
                        saveImageToGallery(appContext, bytes)

                    } else {
                        Log.d("DetectionVM", "Image unchanged or null")
                    }

                } catch (e: Exception) {
                    Log.e("DetectionVM", "Polling error: ${e.message}")
                }

                delay(1000L) // delay polling
            }

            Log.d("DetectionVM", "Polling stopped")
        }

    }

    fun stopPolling() {
        _pollingActive.value = false
        Log.d("DetectionVM", "Polling flag set to false")
    }

    private fun saveImageToGallery(context: Context, imageBytes: ByteArray): File? {
        return try {
            val dir = File(context.filesDir, "gallery_images")
            if (!dir.exists()) dir.mkdirs()

            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
            val file = File(dir, "IMG_$timestamp.jpg")

            file.writeBytes(imageBytes)
            Log.d("DetectionVM", "Image saved to ${file.absolutePath}")
            file
        } catch (e: Exception) {
            Log.e("DetectionVM", "Failed to save image: ${e.message}")
            null
        }
    }
}


