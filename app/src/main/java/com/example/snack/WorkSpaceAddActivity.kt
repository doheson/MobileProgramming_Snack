package com.example.snack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.snack.databinding.ActivityWorkSpaceAddBinding
import com.example.snack.databinding.ActivityWorkSpaceListBinding

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
        }
    }
}