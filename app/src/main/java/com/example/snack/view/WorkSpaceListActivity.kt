package com.example.snack.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.snack.databinding.ActivityWorkSpaceListBinding
import com.google.firebase.auth.FirebaseAuth

class WorkSpaceListActivity : AppCompatActivity() {
    lateinit var binding: ActivityWorkSpaceListBinding
    private val firebaseAuth: FirebaseAuth? = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkSpaceListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init(){
        binding.apply {
            addWorkspace.setOnClickListener {
                val intent = Intent(this@WorkSpaceListActivity, WorkSpaceAddActivity::class.java)
                startActivity(intent)
            }
            logout.setOnClickListener {
                firebaseAuth?.signOut()
                val intent = Intent(this@WorkSpaceListActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}