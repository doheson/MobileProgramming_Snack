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
import com.example.snack.adapter.MessageAdapter
import com.example.snack.adapter.MessageUserAdapter
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


class MessageActivity : AppCompatActivity() {
    private lateinit var adapter: ChMessageAdapter
    lateinit var binding: ActivityMessageBinding
    var comments: ArrayList<ChatModel.Comment> =ArrayList()
    var channelTitle = ""
    //chat things
    private var chatRoomUid //채팅방 하나 id
            : String? = null
    private var myuid //나의 id
            : String? = null
    private var destUid //상대방 uid
            : String? = null
     var firebaseDatabase: FirebaseDatabase? = null
     var firebaseAuth: FirebaseAuth? = null
     var destUser: User? = null

    private lateinit var database: DatabaseReference
    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

    }

    private fun init(){
        channelTitle = intent.getStringExtra("cannelTitle").toString()

        binding.apply {
            title.text = channelTitle
            if(editmsg.text.toString()==null)
                sendmsgbtn.isEnabled=false
            else
                sendmsgbtn.isEnabled=true
        }
        firebaseDatabase= FirebaseDatabase.getInstance()
        database=Firebase.database.getReference()
        firebaseAuth = FirebaseAuth.getInstance()
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

            sendmsgbtn.setOnClickListener {
                var chatmodel=ChatModel()
                chatmodel.users.put(myuid,true)
                chatmodel.users.put(destUid,true)

                Toast.makeText(this@MessageActivity,"채팅방생성직전",Toast.LENGTH_SHORT).show()
                if(chatRoomUid==null){
                    Toast.makeText(this@MessageActivity,"채팅방생성",Toast.LENGTH_SHORT).show()
                    sendmsgbtn.isEnabled=false
                    firebaseDatabase!!.reference.child("chatrooms").push().setValue(chatmodel).addOnSuccessListener {
                        checkChatRoom()
                    }
                }
                else{
                    sendMsgToDataBase()
                }
            }
        }


    }

    fun checkChatRoom(){

        val postlistener= object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(dataSnapshot:DataSnapshot in snapshot.getChildren()){
                   val chatModel: ChatModel? =dataSnapshot.getValue<ChatModel>()
                           if(chatModel!!.users.containsKey(destUid)){
                               chatRoomUid=dataSnapshot.key
                               getMessageList()

                               binding.apply {

                                   sendmsgbtn.isEnabled=true
                                   adapter=ChMessageAdapter(comments,myuid!!,destUid!!,chatRoomUid!!)
                                   recyclerView.layoutManager =LinearLayoutManager(this@MessageActivity,LinearLayoutManager.VERTICAL,false)
                                   recyclerView.adapter = adapter
                                   sendMsgToDataBase()
                               }
                           }

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }

        firebaseDatabase!!.getReference().child("chatrooms").orderByChild("users/"+myuid).equalTo(true).addListenerForSingleValueEvent(postlistener)
    }

    fun getMessageList(){
        val postlistener= object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                comments?.clear()
                for(dataSnapshot in snapshot.children){
                    comments?.add(dataSnapshot.getValue<ChatModel.Comment>()!!)
                }
                adapter.notifyDataSetChanged()
                binding.recyclerView.scrollToPosition(comments?.size?.minus(1)!!)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }
        firebaseDatabase!!.getReference().child("chatrooms").child(chatRoomUid!!).child("comments").addValueEventListener(postlistener)

    }

    fun sendMsgToDataBase(){
        binding.apply {
            if(!editmsg.text.toString().equals("")){
                var comment=ChatModel.Comment()
                comment.uid=myuid
                comment.message=editmsg.text.toString()
                comment.timestamp=ServerValue.TIMESTAMP
                firebaseDatabase!!.getReference().child("chatrooms").child(chatRoomUid!!).child("comments").push().setValue(comment).addOnSuccessListener {
                    editmsg.setText("")
                }

            }
        }
    }


}


