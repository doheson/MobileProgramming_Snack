package com.example.snack.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.snack.data.UserIdData
import com.example.snack.databinding.RowSearchMemberBinding

class AddUserAdapter (val items:ArrayList<UserIdData>)
    : RecyclerView.Adapter<AddUserAdapter.MyViewHolder>(){
    interface OnItemClickListener{
        fun OnItemClick(holder: MyViewHolder, view: View, data: UserIdData, position: Int)
    }

    var itemClickListener:OnItemClickListener?= null

    inner class MyViewHolder(val binding: RowSearchMemberBinding): RecyclerView.ViewHolder(binding.root) {
        init{
            binding.add.setOnClickListener {
                itemClickListener?.OnItemClick(this, it, items[adapterPosition], adapterPosition)
            }
        }
    }

    fun clearData(){
        items.clear()
    }

    fun addData(d: ArrayList<UserIdData>){
        clearData()
        items.addAll(d)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = RowSearchMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.editname.text = items[position].userId
    }

}
