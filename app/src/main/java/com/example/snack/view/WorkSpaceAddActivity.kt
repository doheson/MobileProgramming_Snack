package com.example.snack.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.snack.data.WorkSpaceList
import com.example.snack.databinding.ActivityWorkSpaceAddBinding
import kotlinx.android.synthetic.main.activity_sign_up.*

class WorkSpaceAddActivity : AppCompatActivity() {
    lateinit var binding: ActivityWorkSpaceAddBinding
    lateinit var wsDBHelper: WorkSpaceDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkSpaceAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        getList()
    }
    fun getList(){
        wsDBHelper.getList()
    }
    fun clearEditText(){
        binding.apply {
            editname.text.clear()
            editchannel.text.clear()
        }
    }
    fun init(){
        wsDBHelper = WorkSpaceDBHelper(this)
        binding.apply {
            addWorkspace2.setOnClickListener {
                //val intent = Intent(this@WorkSpaceAddActivity, AddMemberActivity::class.java)
                //startActivity(intent)
                val wsname = editname.text.toString()
                val channel = editchannel.text.toString()
                val workspace = WorkSpaceList(0, wsname, channel)
                val result = wsDBHelper.insertName(workspace)
                clearEditText()
            }
        }
    }
}