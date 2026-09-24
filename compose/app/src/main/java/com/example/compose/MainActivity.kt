package com.example.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AddThreeNumbers()
        }
    }
}

@Composable
fun AddThreeNumbers() {

    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }
    var num3 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(24.dp)
    ) {

        TextField(
            value = num1,
            onValueChange = { num1 = it },
            label = { Text("First number") }
        )

        TextField(
            value = num2,
            onValueChange = { num2 = it },
            label = { Text("Second number") }
        )

        TextField(
            value = num3,
            onValueChange = { num3 = it },
            label = { Text("Third number") }
        )

        Button(
            onClick = {
                val a = num1.toDoubleOrNull() ?: 0.0
                val b = num2.toDoubleOrNull() ?: 0.0
                val c = num3.toDoubleOrNull() ?: 0.0

                result = "Result: ${a + b + c}"
            }
        ) {
            Text("Add")
        }

        Text(result)
    }
}