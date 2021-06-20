package com.example.snack.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.snack.databinding.RowChannelMessageBinding
import com.example.snack.databinding.RowUserBinding
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

class MessageAdapter(my:String,dest:String,chatroom:String) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
    var comments: ArrayList<ChatModel.Comment> =ArrayList()
    val myuid=my
    val destUid=dest
    val chatRoomUid=chatroom
    var firebaseDatabase= FirebaseDatabase.getInstance()
    var destUser: User? = null
    val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("yyy.MM.dd HH:mm")
    inner class ViewHolder(val binding: RowChannelMessageBinding): RecyclerView.ViewHolder(binding.root) {
        init{

            getDestUid()
        }
    }

    private fun getDestUid() {
        val postlistener= object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                destUser=snapshot.getValue<User>()
                getMessageList()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }
        firebaseDatabase!!.getReference().child("users").child(destUid!!).addListenerForSingleValueEvent(postlistener)
    }
    fun getMessageList(){
        val postlistener= object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                comments?.clear()
                for(dataSnapshot in snapshot.children){
                    comments?.add(dataSnapshot.getValue<ChatModel.Comment>()!!)
                }
                notifyDataSetChanged()
                //binding.recyclerView.scrollToPosition(comments?.size?.minus(1)!!)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }
        firebaseDatabase!!.getReference().child("chatrooms").child(chatRoomUid!!).child("comments").addValueEventListener(postlistener)

    }
    fun getDateTime(position: Int):String {
        val unixTime = comments!!.get(position).timestamp.toString()
        val date = Date(unixTime)
        simpleDateFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        return simpleDateFormat.format(date)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageAdapter.ViewHolder {
        val view = RowChannelMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(comments?.get(position)?.uid.equals(myuid)){
            Log.i("testiadapter",myuid+comments?.get(position)?.message)
            holder.binding.editname.text=myuid
            holder.binding.textingmsg.text=comments?.get(position)?.message

        }
        else{
            holder.binding.editname.text=destUid
            holder.binding.textingmsg.text=comments?.get(position)?.message

        }
    }

}