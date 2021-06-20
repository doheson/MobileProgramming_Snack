package com.example.snack.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.snack.DBHelper.User_WorkSpaceDBHelper
import com.example.snack.DBHelper.WorkSpaceDBHelper
import com.example.snack.adapter.WorkSpaceAddAdapter
import com.example.snack.data.User_WorkSpaceData
import com.example.snack.data.WorkSpaceList
import com.example.snack.databinding.ActivityWorkSpaceAddBinding
import com.google.firebase.auth.FirebaseAuth

class WorkSpaceAddActivity : AppCompatActivity() {
    lateinit var binding: ActivityWorkSpaceAddBinding

    lateinit var wsDBHelper: WorkSpaceDBHelper
    lateinit var adapter: WorkSpaceAddAdapter
    var workTitle = ""
    var isDetail = false

    lateinit var userWorkspacedbhelper: User_WorkSpaceDBHelper
    private val firebaseAuth: FirebaseAuth? = FirebaseAuth.getInstance()
    var userEmail = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkSpaceAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init(){
        var user = firebaseAuth?.currentUser;

        if (user != null) {
            userEmail = user.email.toString()
            Log.e("userName check", userEmail)
        } else {
            // No user is signed in.
        }
        if(intent.hasExtra("workName")){
            workTitle = intent.getStringExtra("workName").toString()
            binding.editname.setText(workTitle)
        }
        if(intent.hasExtra("isDetail")){
            isDetail = intent.getBooleanExtra("isDetail",true)
        }

        wsDBHelper = WorkSpaceDBHelper(this)
        userWorkspacedbhelper = User_WorkSpaceDBHelper(this)

        binding.apply {
            workTitle = editname.text.toString()
            recyclerView.layoutManager = LinearLayoutManager(this@WorkSpaceAddActivity,LinearLayoutManager.VERTICAL,false)

            adapter = WorkSpaceAddAdapter(ArrayList<String>())
            adapter.itemClickListener = object :WorkSpaceAddAdapter.OnItemClickListener{ //채널 삭제하기
                override fun OnItemClick(holder: WorkSpaceAddAdapter.MyViewHolder, view: View, data: String, position: Int) {
                    adapter.deleteChannel(position)
                }
            }
            recyclerView.adapter = adapter

            add.setOnClickListener { //채널 추가하기
                var channelName = editchannel.text.toString()
                adapter.addChannel(channelName)
                editchannel.text.clear()
            }

            addWorkspace2.setOnClickListener {
                if(adapter.items.size != 0){
                    for(i in 0 until adapter.getItemCount()){
                        wsDBHelper.insertName(WorkSpaceList(0,binding.editname.text.toString(), adapter.items[i]))
                    }
                    userWorkspacedbhelper.insertData(User_WorkSpaceData(userEmail, binding.editname.text.toString()))
                }

                if(isDetail){
                    val intent = Intent(this@WorkSpaceAddActivity, DetailWorkspaceActivity::class.java)
                    intent.putExtra("workName", binding.editname.text.toString())
                    startActivity(intent)
                }else{
                    val intent = Intent(this@WorkSpaceAddActivity, AddMemberActivity::class.java)
                    intent.putExtra("workName", binding.editname.text.toString())
                    startActivity(intent)
                }
            }
        }
    }
}