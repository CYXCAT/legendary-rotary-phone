package com.example.sessionchat.session

import android.content.Context
import kotlin.random.Random

class SessionStore(context: Context) {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getSessionId(): String {
        val stored = prefs.getString(KEY_SESSION_ID, null)
        if (!stored.isNullOrBlank()) {
            return stored
        }
        return setSessionId(createSessionId())
    }

    fun setSessionId(sessionId: String): String {
        val value = sessionId.trim().ifBlank { createSessionId() }
        prefs.edit().putString(KEY_SESSION_ID, value).apply()
        return value
    }

    fun createSessionId(): String {
        val timePart = System.currentTimeMillis().toString(36)
        val randomPart = Random.nextInt(0, 0xffffff).toString(36).padStart(6, '0')
        return "sess_${timePart}_$randomPart"
    }

    companion object {
        private const val PREFS_NAME = "session_chat"
        private const val KEY_SESSION_ID = "chat_session_id"
    }
}
