package com.example.lab7

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.ListView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 自動處理邊界間距
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 初始化元件
        val spinner: Spinner = findViewById(R.id.spinner)
        val listView: ListView = findViewById(R.id.listView)
        val gridView: GridView = findViewById(R.id.gridView)

        // 資料初始化
        val count = List(10) { "${it + 1} 個" } // 使用高階函數初始化數量
        val items = loadItems() // 使用方法載入水果資料

        // 設定 Spinner
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, count)

        // 設定 GridView
        gridView.numColumns = 3
        gridView.adapter = MyAdapter(this, items, R.layout.adapter_vertical)

        // 設定 ListView
        listView.adapter = MyAdapter(this, items, R.layout.adapter_horizontal)
    }

    private fun loadItems(): List<Item> {
        val itemList = mutableListOf<Item>()
        val priceRange = 10..100
        val array = resources.obtainTypedArray(R.array.image_list)
        for (index in 0 until array.length()) {
            val photo = array.getResourceId(index, 0)
            val name = "水果 ${index + 1}"
            val price = priceRange.random()
            itemList.add(Item(photo, name, price))
        }
        array.recycle()
        return itemList
    }
}

