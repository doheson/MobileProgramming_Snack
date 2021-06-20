package com.example.snack.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.snack.DBHelper.User_WorkSpaceDBHelper
import com.example.snack.DBHelper.WorkSpaceDBHelper
import com.example.snack.adapter.ChannelAdapter
import com.example.snack.adapter.MessageUserAdapter
import com.example.snack.databinding.ActivityDetailWorkspaceBinding

class DetailWorkspaceActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailWorkspaceBinding
    var workTitle = ""

    lateinit var workSpaceDBHelper: WorkSpaceDBHelper
    lateinit var adapter1: ChannelAdapter
    var array = ArrayList<String>()

    lateinit var userWorkspacedbhelper: User_WorkSpaceDBHelper
    lateinit var adapter2: MessageUserAdapter
    var array2 = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailWorkspaceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    fun init(){
        workTitle = intent.getStringExtra("workName").toString()

        //배열 초기화
        workSpaceDBHelper = WorkSpaceDBHelper(this)
        array.addAll(workSpaceDBHelper.findChannel(workTitle))

        userWorkspacedbhelper = User_WorkSpaceDBHelper(this)
        array2.addAll(userWorkspacedbhelper.findUser(workTitle))

        Log.e("채널 사이즈", array.size.toString())

        binding.apply {
            title.text = workTitle

            //채널 리사이클러뷰
            recyclerView.layoutManager = LinearLayoutManager(this@DetailWorkspaceActivity, LinearLayoutManager.VERTICAL,false)

            adapter1 = ChannelAdapter(array)
            adapter1.itemClickListener = object :ChannelAdapter.OnItemClickListener{
                override fun OnItemClick(holder: ChannelAdapter.MyViewHolder, view: View, data: String, position: Int) {
                    val i = Intent(this@DetailWorkspaceActivity, MessageActivity::class.java)
                    i.putExtra("cannelTitle", adapter1.items[position])
                    startActivity(i)
                }
            }
            recyclerView.adapter = adapter1

            // 메세지 사용자 리사이클러뷰
            recyclerView2.layoutManager = LinearLayoutManager(this@DetailWorkspaceActivity, LinearLayoutManager.VERTICAL,false)

            adapter2 = MessageUserAdapter(array2)
            adapter2.itemClickListener = object :MessageUserAdapter.OnItemClickListener{
                override fun OnItemClick(holder: MessageUserAdapter.MyViewHolder, view: View, data: String, position: Int) {
                    // 메세지 화면으로 전환
                    val i = Intent(this@DetailWorkspaceActivity, MessageActivity::class.java)
                    i.putExtra("cannelTitle", adapter2.items[position])
                    startActivity(i)
                }
            }
            recyclerView2.adapter = adapter2


            addMember.setOnClickListener {  //팀원 추가
                val i = Intent(this@DetailWorkspaceActivity, AddMemberActivity::class.java)
                i.putExtra("isDetail", true)
                i.putExtra("workName", workTitle)
                startActivity(i)
            }

            editchannel.setOnClickListener { //채널 추가
                val i = Intent(this@DetailWorkspaceActivity, WorkSpaceAddActivity::class.java)
                i.putExtra("isDetail", true)
                i.putExtra("workName", workTitle)
                startActivity(i)
            }

        }
    }
}