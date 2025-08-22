package com.example.final_project_apk.network

import android.content.Context
import android.util.Log
import com.example.final_project_apk.network.PreferenceManager
import io.socket.client.IO
import io.socket.client.Socket

object SocketHandler {
    private var mSocket: Socket? = null

    fun initSocket(context: Context) {
        try {
            val (ip, socketPort, _) = PreferenceManager.getActiveIpEntry(context) ?: run {
                Log.e("SocketHandler", "No active IP entry found")
                return
            }

            val baseUrl = "http://$ip:$socketPort"

            val options = IO.Options().apply {
                reconnection = true
                forceNew = true
            }

            if (mSocket?.connected() == true) {
                closeSocket()
            }

            mSocket = IO.socket(baseUrl, options)
            mSocket?.connect()

            Log.d("SocketHandler", "Socket initialized to $baseUrl")
        } catch (e: Exception) {
            Log.e("SocketHandler", "Failed to init socket: ${e.message}")
            e.printStackTrace()
        }
    }

    fun getSocket(): Socket? = mSocket

    fun closeSocket() {
        try {
            mSocket?.disconnect()
            mSocket?.close()
            mSocket = null
            Log.d("SocketHandler", "Socket closed")
        } catch (e: Exception) {
            Log.e("SocketHandler", "Error closing socket: ${e.message}")
        }
    }
}