package com.santeut.ui.map

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.google.firebase.database.tubesock.WebSocketMessage
import com.google.gson.Gson
import com.santeut.domain.usecase.HikingUseCase
import com.santeut.domain.usecase.PartyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject

@HiltViewModel
class HikingViewModel @Inject constructor(
    private val partyUseCase: PartyUseCase,
    private val hikingUseCase: HikingUseCase
) : ViewModel() {

    // party ID 받아와서 url 에 추가해주기
    val partyId = 1

    // Web Socket
    private val webSocketUrl = "wss://k10e201.p.ssafy.io/api/hiking/chat/rooms/${partyId}"
    private var webSocket: WebSocket? = null



    fun startWebSocket(
        updateLocation: (String, Double, Double, String?) -> Unit,
        token: String,
        showAlert: MutableState<Boolean>,
        alertMessage: MutableState<String>
    ) {
        val request = Request.Builder()
            .url(webSocketUrl)
            .addHeader("Authorization", "Bearer $token")
            .build()

        val listener = object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.d("WebSocket", "Received message: $text")
                try {
                    val message = Gson().fromJson(text, WebSocketMessage::class.java)
                    updateLocation(message.userNickname, message.lat.toDouble(), message.lng.toDouble(), message.userProfile)

                    if (message.type == "offCourse") {
                        showAlert.value = true
                        alertMessage.value = "${message.userNickname}님이 경로를 이탈하셨습니다."
                    }
                } catch (e: Exception) {
                    Log.e("WebSocket", "Error parsing message: ${e.localizedMessage}", e)
                }
            }

            override fun onOpen(webSocket: WebSocket, response: Response) {
                webSocket.send("{\"type\": \"enter\"}")
                Log.d("WebSocket", "WebSocket opened and initial message sent.")
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                Log.d("WebSocket", "WebSocket is closing: $reason")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.e("WebSocket", "WebSocket connection failure: ${t.localizedMessage}", t)
            }
        }

        webSocket = OkHttpClient().newWebSocket(request, listener)
    }

    fun stopWebSocket() {
        webSocket?.send("{\"type\": \"quit\"}")
        webSocket?.close(1000, "Activity Ended")
        webSocket = null
    }
}