package com.example.snack.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.snack.R
import com.example.snack.databinding.ActivityAddMemberBinding
import com.example.snack.databinding.ActivityChannelBinding

class ChannelActivity : AppCompatActivity() {
    lateinit var binding: ActivityChannelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChannelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){

    }
}