package com.example.snack.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.snack.R
import com.example.snack.databinding.ActivityAddMemberBinding
import com.example.snack.databinding.ActivityMainBinding

class AddMemberActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddMemberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    fun init(){
        binding.apply {
            addWorkspace.setOnClickListener {
                val intent = Intent(this@AddMemberActivity, WorkSpaceListActivity::class.java)
                startActivity(intent)
            }
        }

    }
}