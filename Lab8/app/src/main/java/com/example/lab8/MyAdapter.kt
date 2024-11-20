package com.example.lab8

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(
    private val contactList: MutableList<Contact>
) : RecyclerView.Adapter<MyAdapter.ContactViewHolder>() {

    // 定義 ViewHolder，包含 View 元件的綁定邏輯
    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.tvName)
        private val phoneTextView: TextView = itemView.findViewById(R.id.tvPhone)
        private val deleteImageView: ImageView = itemView.findViewById(R.id.imgDelete)

        // 綁定資料到 View，並設置刪除按鈕的監聽器
        fun bind(contact: Contact, onDeleteClick: (Contact) -> Unit) {
            nameTextView.text = contact.name
            phoneTextView.text = contact.phone
            deleteImageView.setOnClickListener { onDeleteClick(contact) }
        }
    }

    // 建立 ViewHolder 並與 Layout 連結
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_row, parent, false)
        return ContactViewHolder(view)
    }

    // 傳回資料數量
    override fun getItemCount(): Int = contactList.size

    // 將資料綁定到 ViewHolder，並設置刪除功能
    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contactList[position]
        holder.bind(contact) { deletedContact ->
            val index = contactList.indexOf(deletedContact)
            if (index != -1) {
                contactList.removeAt(index)
                notifyItemRemoved(index) // 使用更高效的通知方式
            }
        }
    }
}
