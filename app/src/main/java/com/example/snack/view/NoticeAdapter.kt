package com.example.snack.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.snack.databinding.RowNoticeBinding

class NoticeAdapter(val items: ArrayList<NoticeData>) :
    RecyclerView.Adapter<NoticeAdapter.MyViewHolder>() {

    interface OnItemClickListener {
        fun OnItemClick(holder: MyViewHolder, view: View, data: NoticeData, position: Int)
    }

    var itemClickListener: OnItemClickListener? = null

    inner class MyViewHolder(val binding: RowNoticeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.noticeTitle.setOnClickListener {
                itemClickListener?.OnItemClick(this, it, items[adapterPosition], adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = RowNoticeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.noticeTitle.text = items[position].noticeTitle
    }

}