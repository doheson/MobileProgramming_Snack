package com.example.snack.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.snack.databinding.ActivityWorkSpaceAddBinding

class WorkSpaceAddActivity : AppCompatActivity() {
    lateinit var binding: ActivityWorkSpaceAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkSpaceAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    fun init(){
        binding.apply {
            addMember.setOnClickListener {
                val intent = Intent(this@WorkSpaceAddActivity, AddMemberActivity::class.java)
                startActivity(intent)
            }
        }
    }
}