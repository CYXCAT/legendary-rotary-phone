package com.example.sessionchat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sessionchat.data.ChatRepository
import com.example.sessionchat.session.SessionStore
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sessionStore = SessionStore(this)
        val repository = ChatRepository()

        setContent {
            var sessionId by remember { mutableStateOf(sessionStore.getSessionId()) }
            var input by remember { mutableStateOf("") }
            var log by remember { mutableStateOf("") }
            var sending by remember { mutableStateOf(false) }
            val scope = rememberCoroutineScope()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text("Session Chat")

                TextField(
                    value = sessionId,
                    onValueChange = { sessionId = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    label = { Text("session_id") },
                    singleLine = true,
                )

                Button(
                    onClick = {
                        sessionId = sessionStore.setSessionId(sessionStore.createSessionId())
                        log = "已开始新会话。\n"
                    },
                    modifier = Modifier.padding(top = 8.dp),
                ) {
                    Text("新会话")
                }

                Text(
                    text = log.ifBlank { "（暂无消息）" },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .verticalScroll(rememberScrollState()),
                )

                TextField(
                    value = input,
                    onValueChange = { input = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("输入消息") },
                    enabled = !sending,
                )

                Button(
                    onClick = {
                        val text = input.trim()
                        if (text.isEmpty() || sending) return@Button

                        val sid = sessionStore.setSessionId(sessionId)
                        sessionId = sid
                        log += "我: $text\n"
                        input = ""
                        sending = true

                        scope.launch {
                            try {
                                val output = repository.sendChat(sid, text)
                                log += "助手: $output\n"
                            } catch (e: Exception) {
                                log += "错误: ${e.message ?: "请求失败"}\n"
                            } finally {
                                sending = false
                            }
                        }
                    },
                    enabled = !sending,
                    modifier = Modifier.padding(top = 8.dp),
                ) {
                    Text(if (sending) "发送中…" else "发送")
                }
            }
        }
    }
}
