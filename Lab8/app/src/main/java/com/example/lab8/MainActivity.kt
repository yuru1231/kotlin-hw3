package com.example.lab8

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    // 延遲初始化 MyAdapter
    private lateinit var adapter: MyAdapter

    // 聯絡人列表
    private val contactList = ArrayList<Contact>()

    // ActivityResultLauncher 用於接收 SecActivity 回傳的資料
    private val secActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { intent ->
                val name = intent.getStringExtra("name").orEmpty()
                val phone = intent.getStringExtra("phone").orEmpty()
                contactList.add(Contact(name, phone)) // 新增聯絡人
                adapter.notifyDataSetChanged() // 更新列表
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // 設置視窗的邊緣屬性
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 綁定元件
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val addButton: Button = findViewById(R.id.btnAdd)

        // 設置 RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        adapter = MyAdapter(contactList)
        recyclerView.adapter = adapter

        // 設置按鈕點擊事件
        addButton.setOnClickListener {
            val intent = Intent(this, SecActivity::class.java)
            secActivityLauncher.launch(intent)
        }
    }
}
