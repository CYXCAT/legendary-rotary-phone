package com.example.trad

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val num1 = findViewById<EditText>(R.id.num1)
        val num2 = findViewById<EditText>(R.id.num2)
        val addButton = findViewById<Button>(R.id.addButton)
        val resultText = findViewById<TextView>(R.id.resultText)

        addButton.setOnClickListener {

            val a = num1.text.toString().toDoubleOrNull() ?: 0.0
            val b = num2.text.toString().toDoubleOrNull() ?: 0.0

            val sum = a + b

            resultText.text = "Result: $sum"
        }
    }
}