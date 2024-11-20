package com.example.lab7

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class MyAdapter(
    context: Context,
    data: List<Item>,
    private val layout: Int
) : ArrayAdapter<Item>(context, layout, data) {

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        // 使用 convertView 來重用已經存在的 view，提升效能
        val view = convertView ?: View.inflate(parent.context, layout, null)
        
        // 取得當前項目的資料
        val item = getItem(position) ?: return view
        
        // 設定圖片
        val imgPhoto: ImageView = view.findViewById(R.id.imgPhoto)
        imgPhoto.setImageResource(item.photo)

        // 設定文字內容
        val tvMsg: TextView = view.findViewById(R.id.tvMsg)
        tvMsg.text = if (layout == R.layout.adapter_vertical) {
            item.name
        } else {
            "${item.name}: ${item.price}元"
        }

        // 返回顯示的視圖
        return view
    }
}
