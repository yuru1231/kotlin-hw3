package com.example.lab8

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sec)

        // 設定系統邊距
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 綁定元件
        val nameEditText = findViewById<EditText>(R.id.edName)
        val phoneEditText = findViewById<EditText>(R.id.edPhone)
        val sendButton = findViewById<Button>(R.id.btnSend)

        // 設定按鈕點擊事件
        sendButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val phone = phoneEditText.text.toString().trim()

            when {
                name.isEmpty() -> showToast("請輸入姓名")
                phone.isEmpty() -> showToast("請輸入電話")
                else -> {
                    // 將輸入資料透過 Intent 回傳
                    val intent = Intent().apply {
                        putExtra("name", name)
                        putExtra("phone", phone)
                    }
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }
        }
    }

    // 顯示 Toast 訊息
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
