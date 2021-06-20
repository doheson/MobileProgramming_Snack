package com.example.snack.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.snack.adapter.ChMessageAdapter
import com.example.snack.adapter.ChannelMessageAdapter
import com.example.snack.adapter.MessageAdapter
import com.example.snack.adapter.MessageUserAdapter
import com.example.snack.databinding.ActivityChannelMessageBinding
import com.example.snack.databinding.ActivityMessageBinding
import com.example.snack.databinding.RowChannelMessageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ChannelMessageActivity : AppCompatActivity() {
    private lateinit var adapter: ChannelMessageAdapter
    lateinit var binding: ActivityChannelMessageBinding
    var comments: ArrayList<ChatModel.Comment> =ArrayList()
    var channelTitle = ""
    //chat things
    private var chatRoomUid //채팅방 하나 id
            : String? = null
    private var myuid //나의 id
            : String? = null
    private var destUid //상대방 uid
            : String? = null
    private var channelmessageuid
            :String? =null
    var firebaseDatabase: FirebaseDatabase? = null
    var firebaseAuth: FirebaseAuth? = null
    var destUser: User? = null

    private lateinit var database: DatabaseReference
    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChannelMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

    }

    private fun init(){
        channelTitle = intent.getStringExtra("cannelTitle").toString()

        binding.apply {
            titlemsg.text = channelTitle
            if(editmsgmsg.text.toString()==null)
                sendmsgbtnmsg.isEnabled=false
            else
                sendmsgbtnmsg.isEnabled=true
        }
        firebaseDatabase= FirebaseDatabase.getInstance()
        database=Firebase.database.getReference()
        firebaseAuth = FirebaseAuth.getInstance()
        channelmessageuid=channelTitle.split("@")[0].trim()
        destUid=channelTitle.split("@")[0].trim()
        database.child("users").child(firebaseAuth?.uid.toString()).get().addOnSuccessListener {
            myuid=it.value.toString()

        }
            .addOnCompleteListener {

                checkChatRoom()
                sendMsg()
            }


    }

    fun sendMsg(){
        binding.apply {

            sendmsgbtnmsg.setOnClickListener {
                var chatmodel=ChatModel()
                chatmodel.users.put(myuid,true)
                chatmodel.users.put(destUid,true)
                Toast.makeText(this@ChannelMessageActivity,"채팅방생성직전",Toast.LENGTH_SHORT).show()
                    sendmsgbtnmsg.isEnabled=false

                        checkChatRoom()


            }
        }


    }

    fun checkChatRoom(){

        val postlistener= object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {


                        getMessageList()

                        binding.apply {

                            sendmsgbtnmsg.isEnabled=true
                            adapter=ChannelMessageAdapter(comments)
                            recyclerViewmsg.layoutManager =LinearLayoutManager(this@ChannelMessageActivity,LinearLayoutManager.VERTICAL,false)
                            recyclerViewmsg.adapter = adapter
                            sendMsgToDataBase()
                        }



            }

            override fun onCancelled(error: DatabaseError) {

            }

        }

        firebaseDatabase!!.getReference().child("channelchatrooms").child(channelmessageuid!!).addListenerForSingleValueEvent(postlistener)
    }

    fun getMessageList(){
        val postlistener= object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                comments?.clear()
                for(dataSnapshot in snapshot.children){
                    comments?.add(dataSnapshot.getValue<ChatModel.Comment>()!!)
                }
                adapter.notifyDataSetChanged()
                binding.recyclerViewmsg.scrollToPosition(comments?.size?.minus(1)!!)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }
        firebaseDatabase!!.getReference().child("channelchatrooms").child(channelmessageuid!!).child("comments").addValueEventListener(postlistener)

    }

    fun sendMsgToDataBase(){
        Log.i("test","sdfs")
        binding.apply {
            if(!editmsgmsg.text.toString().equals("")){
                var comment=ChatModel.Comment()
                comment.uid=myuid
                comment.message=editmsgmsg.text.toString()
                comment.timestamp=ServerValue.TIMESTAMP
                firebaseDatabase!!.getReference().child("channelchatrooms").child(channelmessageuid!!).child("comments").push().setValue(comment).addOnSuccessListener {
                    editmsgmsg.setText("")
                }

            }
        }
    }


}


