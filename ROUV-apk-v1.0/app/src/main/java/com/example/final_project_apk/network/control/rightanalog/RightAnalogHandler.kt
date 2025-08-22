package com.example.final_project_apk.network.control.rightanalog

import androidx.lifecycle.ViewModel
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RightAnalogHandler : ViewModel() {
    private val _stickPosition = MutableStateFlow(Offset.Zero)
    val stickPosition: StateFlow<Offset> = _stickPosition

    private val _normalizedPosition = MutableStateFlow(Offset.Zero)
    val normalizedPosition: StateFlow<Offset> = _normalizedPosition

    fun updateStickPosition(dragAmount: Offset, maxDistancePx: Float) {
        val currentPos = _stickPosition.value
        val newPosition = currentPos + dragAmount

        val distance = newPosition.getDistance()
        val limitedPosition = if (distance <= maxDistancePx) {
            newPosition
        } else {
            newPosition * (maxDistancePx / distance)
        }

        _stickPosition.value = limitedPosition
        _normalizedPosition.value = Offset(
            limitedPosition.x / maxDistancePx,
            -limitedPosition.y / maxDistancePx
        )
    }

    fun resetStick() {
        _stickPosition.value = Offset.Zero
        _normalizedPosition.value = Offset.Zero
    }
}
