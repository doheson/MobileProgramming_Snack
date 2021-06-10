package com.example.snack.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.snack.DBHelper.WorkSpaceDBHelper
import com.example.snack.adapter.WorkSpaceAddAdapter
import com.example.snack.data.WorkSpaceList
import com.example.snack.databinding.ActivityWorkSpaceAddBinding


class WorkSpaceAddActivity : AppCompatActivity() {
    lateinit var binding: ActivityWorkSpaceAddBinding

    lateinit var wsDBHelper: WorkSpaceDBHelper
    lateinit var adapter: WorkSpaceAddAdapter
    var workTitle = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkSpaceAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init(){
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

            addWorkspace2.setOnClickListener { //DB 추가하기

            }
        }


        wsDBHelper = WorkSpaceDBHelper(this)
        binding.apply {
            addWorkspace2.setOnClickListener {
                for(i in 0 until adapter.getItemCount()){
                    wsDBHelper.insertName(WorkSpaceList(0,workTitle, adapter.items[i]))
                }

                val intent = Intent(this@WorkSpaceAddActivity, AddMemberActivity::class.java)
                startActivity(intent)
            }
        }
    }
}