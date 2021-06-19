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

class ChMessageAdapter(val items: ArrayList<ChatModel.Comment>,my:String, dest:String, chatroom:String): RecyclerView.Adapter<ChMessageAdapter.ViewHolder>() {

    val myuid=my
    val destUid=dest
    val chatRoomUid=chatroom
    var firebaseDatabase= FirebaseDatabase.getInstance()
    var destUser: User? = null

    inner class ViewHolder(itemview: View) :RecyclerView.ViewHolder(itemview){
        val editname: TextView =itemView.findViewById(R.id.editname)
        val textingmsg: TextView =itemView.findViewById(R.id.textingmsg)

        init{
            Log.i("testiadapter",myuid+destUid)

        }

    }
//    private fun getDestUid() {
//        val postlistener= object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                destUser=snapshot.getValue<User>()
//                getMessageList()
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//
//        }
//        firebaseDatabase!!.getReference().child("users").child(destUid!!).addListenerForSingleValueEvent(postlistener)
//    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChMessageAdapter.ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.row_channel_message,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChMessageAdapter.ViewHolder, position: Int) {
        if(items?.get(position)?.uid.equals(myuid)){
            holder.editname.text=myuid
            holder.textingmsg.text=items?.get(position)?.message
            holder.textingmsg.setTextColor(Color.DKGRAY)

        }
        else{
            holder.editname.text=destUid
            holder.textingmsg.text=items?.get(position)?.message
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}