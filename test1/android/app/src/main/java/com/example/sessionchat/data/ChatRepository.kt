package com.example.sessionchat.data

class ChatRepository(
    private val api: ChatApi = NetworkModule.createChatApi(),
) {
    suspend fun sendChat(sessionId: String, input: String): String {
        val response = api.chat(ChatRequest(sessionId = sessionId, input = input))
        response.error?.let { throw IllegalStateException(it) }
        return response.output?.ifBlank { "(空回复)" } ?: "(空回复)"
    }
}
