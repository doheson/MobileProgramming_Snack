package com.example.snack.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.snack.databinding.RowChannelBinding

class WorkSpaceAddAdapter (val items:ArrayList<String>)
    : RecyclerView.Adapter<WorkSpaceAddAdapter.MyViewHolder>(){
    interface OnItemClickListener{
        fun OnItemClick(holder: MyViewHolder, view: View, data: String, position: Int)
    }

    var itemClickListener:OnItemClickListener?= null

    inner class MyViewHolder(val binding: RowChannelBinding):RecyclerView.ViewHolder(binding.root) {
        init{
            binding.delete.setOnClickListener {
                itemClickListener?.OnItemClick(this, it, items[adapterPosition], adapterPosition)
            }
        }
    }

    fun addChannel(cName:String){
        items.add(cName)
        notifyDataSetChanged()
    }

    fun deleteChannel(position:Int){
        items.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = RowChannelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.editname.text = items[position]
    }
}
