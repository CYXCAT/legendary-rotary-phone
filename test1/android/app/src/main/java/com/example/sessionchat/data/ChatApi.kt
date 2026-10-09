package com.example.sessionchat.data

import retrofit2.http.Body
import retrofit2.http.POST

interface ChatApi {
    @POST("chat")
    suspend fun chat(@Body body: ChatRequest): ChatResponse
}
