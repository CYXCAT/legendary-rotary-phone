package com.example.sessionchat.data

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {
    /**
     * Prefer 127.0.0.1 with: adb reverse tcp:5000 tcp:5000
     * (more reliable than 10.0.2.2 on some emulator/host setups)
     */
    const val DEFAULT_BASE_URL = "http://127.0.0.1:5000/"

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    fun createChatApi(baseUrl: String = DEFAULT_BASE_URL): ChatApi {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        val client = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ChatApi::class.java)
    }
}
