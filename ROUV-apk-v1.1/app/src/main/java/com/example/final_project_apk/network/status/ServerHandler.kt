package com.example.final_project_apk.network.status

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.final_project_apk.network.SocketHandler
import com.example.final_project_apk.network.PreferenceManager
import io.socket.client.Socket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONObject


class ServerHandler(application: Application) : AndroidViewModel(application) {
    private var socket: Socket? = null
    private var isSetupConnection = false

    private val _updateStatus = MutableStateFlow<UpdateStatus>(UpdateStatus.Loading)
    val updateStatus: StateFlow<UpdateStatus> = _updateStatus

    private val _maxStep = MutableStateFlow(0)
    val maxStep: StateFlow<Int> = _maxStep

    private val _detectedCount = MutableStateFlow(0)
    val detectedCount: StateFlow<Int> = _detectedCount

    private val _currentStep = MutableStateFlow(0)
    val currentStep: StateFlow<Int> = _currentStep

    private val _fpsState = MutableStateFlow(0f)
    val fpsState: StateFlow<Float> = _fpsState

    private val _pwmvalue = MutableStateFlow(0)
    val pwmValue : StateFlow<Int> = _pwmvalue

    init {
        initializeSocket()
    }

    private fun initializeSocket() {
        val context = getApplication<Application>().applicationContext
        SocketHandler.initSocket(context)
        socket = SocketHandler.getSocket() ?: return

        setupConnectionListeners()
        setupStatusListener()
    }

    private fun setupConnectionListeners() {
        if (!isSetupConnection) {
            socket?.on(Socket.EVENT_CONNECT) {
                Log.d("Socket.IO", "Connected")
                if (socket?.connected() == true) {
                    val updateAndroidStat = JSONObject().apply {
                        put("android", "Connected")
                    }
                    socket?.emit("status", updateAndroidStat)
                }
                isSetupConnection = true
            }

            socket?.on(Socket.EVENT_DISCONNECT) {
                Log.d("Socket.IO", "Disconnected")
            }
        }
    }

    private fun setupStatusListener() {
        socket?.on("update_status") { args ->
            try {
                val data = when (val raw = args[0]) {
                    is JSONObject -> raw
                    is String -> JSONObject(raw)
                    is Map<*, *> -> JSONObject(raw)
                    else -> throw IllegalArgumentException("Unknown format")
                }
                _updateStatus.value = UpdateStatus.Success(
                    data.getString("android"),
                    data.getString("movement"),
                    data.getString("ballast"),
                    data.getString("last_updated")
                )
            } catch (e: Exception) {
                Log.e("StatusHandler", "Parsing error: ${e.message}")
            }
        }
        socket?.on("dpad_step") { args ->
            try {
                val data = when (val raw = args[0]) {
                    is JSONObject -> raw
                    is String -> JSONObject(raw)
                    is Map<*, *> -> JSONObject(raw)
                    else -> throw IllegalArgumentException("Unknown format")
                }
                _currentStep.value = data.getInt("step")

            } catch (e: Exception) {
                Log.e("StatusHandlerDpad", "Parsing error: ${e.message}")
            }
        }

        socket?.on("total_detected") { args ->
            try {
                val rawData = args.getOrNull(0)
                val json = when (rawData) {
                    is JSONObject -> rawData
                    is String -> JSONObject(rawData)
                    is Map<*, *> -> JSONObject(rawData)
                    else -> {
                        Log.e("SocketDetected", "Unknown format: $rawData")
                        return@on
                    }
                }
                Log.d("SocketFpsDetected", "Detected Fps: $rawData")
                val count = json.optInt("count", -1)
                val fps = json.optDouble("fps", -1.0)

                if (count >= 0) {
                    _detectedCount.value = count
                    Log.d("SocketDetected", "Detected Count: $count")
                }
                if (fps >= 0) {
                    _fpsState.value = fps.toFloat()
                    Log.d("SocketFPS", "FPS: $fps")
                }
            } catch (e: Exception) {
                Log.e("SocketTotalDetected", "Parsing error: ${e.message}")
            }
        }
    }

    fun updateMaxStepFromSettings(newValue: Int) {
        val context = getApplication<Application>().applicationContext
        PreferenceManager.setMaxStep(context, newValue)
        _maxStep.value = newValue

        emitHandler("leftdpad_maxstep", newValue)
        Log.d("MAXSTEP", "Updated maxStep from Settings: $newValue")
    }

    fun updatePwmFromSettings(pwmVal: Int) {
        val context = getApplication<Application>().applicationContext
        PreferenceManager.setPwmValue(context, pwmVal)
        _pwmvalue.value = pwmVal

        val json = JSONObject()
        json.put("pwm", pwmVal)  // Simpel tanpa "type"
        emitHandler("pwm_value", json)  // Pastikan event "update_pwm" sesuai dengan Flask
        Log.d("PWM", "Updated PWM from Settings: $pwmVal")
    }

    fun connect() {
        if (socket?.connected() != true) {
            socket?.connect()
        }
    }

    fun disconnect() {
        if (socket?.connected() == true) {
            socket?.disconnect()
        }
    }

    fun emitHandler(event: String, vararg contents: Any) {
        try {
            if (socket?.connected() == true) {
                socket?.emit(event, *contents)
            }
        } catch (e: Exception) {
            Log.e("Socket", "Emit error: ${e.message}")
        }
    }
}

sealed class UpdateStatus {
    object Loading : UpdateStatus()
    data class Success(
        val android: String,
        val movement: String,
        val ballast: String,
        val lastUpdated: String
    ) : UpdateStatus()
}