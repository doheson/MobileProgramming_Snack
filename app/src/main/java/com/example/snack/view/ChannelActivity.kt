package com.example.snack.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.snack.databinding.ActivityChannelBinding

class ChannelActivity : AppCompatActivity() {
    lateinit var binding: ActivityChannelBinding

    var channelTitle = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChannelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        channelTitle = intent.getStringExtra("cannelTitle").toString()

        binding.apply {
            title.text = channelTitle
        }
    }
}