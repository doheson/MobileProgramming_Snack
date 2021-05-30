package com.example.snack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.snack.databinding.ActivityMainBinding
import com.example.snack.databinding.ActivitySignUpBinding
import com.example.snack.databinding.ActivityWorkSpaceListBinding

class WorkSpaceListActivity : AppCompatActivity() {
    lateinit var binding: ActivityWorkSpaceListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkSpaceListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init(){
        binding.apply {
            addWorkSpace.setOnClickListener {
                val intent = Intent(this@WorkSpaceListActivity, WorkSpaceAddActivity::class.java)
                startActivity(intent)
            }
        }
    }
}