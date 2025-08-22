package com.example.final_project_apk.network.control.leftdpad

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LeftDPad : ViewModel(){

    private val _isHolding = MutableStateFlow(false)

    private val _direction = MutableStateFlow<StepperDirection?>(null)

    private var _stepIndex = 0
    private val _stepIndexMut = MutableStateFlow(_stepIndex)
    val stepIndex : StateFlow<Int> = _stepIndexMut

    fun startHolding(
        direction: StepperDirection,
        maxStep: Int,
        onStepChanged: (Int) -> Unit // Tambahkan ini
    ) {
        _isHolding.value = true
        _direction.value = direction

        viewModelScope.launch {
            while (_isHolding.value) {
                if ((direction == StepperDirection.UP && _stepIndex < maxStep) ||
                    (direction == StepperDirection.DOWN && _stepIndex > 0)) {

                    sendStepperCommand(direction)
                    if (direction == StepperDirection.UP) {
                        _stepIndex += 5
                    } else {
                        _stepIndex -= 5
                    }

                    _stepIndexMut.value = _stepIndex
                    onStepChanged(_stepIndex) // ← Kirim langsung saat berubah
                } else {
                    stopHolding()
                }

                delay(100)
            }
        }
    }



    fun stopHolding() {
        _isHolding.value = false
        _direction.value = null
        sendStepperCommand(StepperDirection.STOP)
    }

    private fun sendStepperCommand(direction: StepperDirection) {
        // Ganti dengan metode kirim ke ESP8266 (via HTTP/Serial)
        println("Kirim perintah: ${direction.name}")
    }
}

enum class StepperDirection {
    UP,
    DOWN,
    STOP
}
