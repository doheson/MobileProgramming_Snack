package com.example.snack.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.snack.data.User_WorkSpaceData
import com.example.snack.databinding.RowWorkspaceBinding

class WorkSpaceListAdpater (val items:ArrayList<User_WorkSpaceData>)
    : RecyclerView.Adapter<WorkSpaceListAdpater.MyViewHolder>(){
    interface OnItemClickListener{
        fun OnItemClick(holder: MyViewHolder, view: View, data: User_WorkSpaceData, position: Int)
    }

    var itemClickListener:OnItemClickListener?= null

    inner class MyViewHolder(val binding: RowWorkspaceBinding): RecyclerView.ViewHolder(binding.root) {
        init{
            binding.root.setOnClickListener {
                itemClickListener?.OnItemClick(this, it, items[adapterPosition], adapterPosition)
            }
        }
    }

    fun clearData(){
        items.clear()
    }

    fun addData(d: ArrayList<User_WorkSpaceData>){
        items.addAll(d)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = RowWorkspaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.editchannel.text = items[position].workSpaceName
        holder.binding.icon.text = items[position].workSpaceName.substring(0,2)
    }
}
