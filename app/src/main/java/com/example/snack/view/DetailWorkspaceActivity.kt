package com.example.snack.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.snack.R
import com.example.snack.databinding.ActivityAddMemberBinding
import com.example.snack.databinding.ActivityDetailWorkspaceBinding

class DetailWorkspaceActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailWorkspaceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailWorkspaceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    fun init(){
    }
}