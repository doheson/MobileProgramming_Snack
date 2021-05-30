package com.example.snack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.snack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val SIGNUP_RESULT = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init(){
        binding.apply {
            signUp.setOnClickListener {
                val intent = Intent(this@MainActivity, SignUpActivity::class.java)
                startActivityForResult(intent, SIGNUP_RESULT)
            }
            login.setOnClickListener {
                val intent2 = Intent(this@MainActivity, WorkSpaceListActivity::class.java)
                startActivity(intent2)
            }
        }
    }
}