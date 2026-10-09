package com.example.sessionchat.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatRequest(
    @SerialName("session_id") val sessionId: String,
    val input: String,
)

@Serializable
data class ChatResponse(
    @SerialName("session_id") val sessionId: String? = null,
    val output: String? = null,
    val error: String? = null,
)
