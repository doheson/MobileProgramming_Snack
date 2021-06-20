package com.example.snack.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.example.snack.R
import com.example.snack.view.ChatModel
import com.example.snack.view.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChannelMessageAdapter(val items: ArrayList<ChatModel.Comment>): RecyclerView.Adapter<ChannelMessageAdapter.ViewHolder>() {



    var firebaseDatabase= FirebaseDatabase.getInstance()
    var destUser: User? = null

    inner class ViewHolder(itemview: View) :RecyclerView.ViewHolder(itemview){
        val editname: TextView =itemView.findViewById(R.id.editname)
        val textingmsg: TextView =itemView.findViewById(R.id.textingmsg)



    }
//    private fun getDestUid() {
//        val postlistener= object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                destUser=snapshot.getValue<User>()
//                getMessageList()
//            }sa
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//
//        }
//        firebaseDatabase!!.getReference().child("users").child(destUid!!).addListenerForSingleValueEvent(postlistener)
//    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelMessageAdapter.ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.row_channel_message,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChannelMessageAdapter.ViewHolder, position: Int) {

            holder.editname.text=items?.get(position)?.uid
            holder.textingmsg.text=items?.get(position)?.message
            holder.textingmsg.setTextColor(Color.DKGRAY)
    }

    override fun getItemCount(): Int {
        return items.size
    }


}