package com.example.snack.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.snack.DBHelper.User_WorkSpaceDBHelper
import com.example.snack.adapter.WorkSpaceListAdpater
import com.example.snack.data.User_WorkSpaceData
import com.example.snack.databinding.ActivityWorkSpaceListBinding
import com.google.firebase.auth.FirebaseAuth

class WorkSpaceListActivity : AppCompatActivity() {
    lateinit var binding: ActivityWorkSpaceListBinding
    private val firebaseAuth: FirebaseAuth? = FirebaseAuth.getInstance()
    var userEmail = ""
    lateinit var userWorkspacedbhelper: User_WorkSpaceDBHelper
    lateinit var adapter: WorkSpaceListAdpater
    var array = ArrayList<User_WorkSpaceData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkSpaceListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init(){
        var user = firebaseAuth?.currentUser;

        if (user != null) {
            userEmail = user.email.toString()
            Log.e("userName check", userEmail)

            userWorkspacedbhelper = User_WorkSpaceDBHelper(this@WorkSpaceListActivity)
            array.addAll(userWorkspacedbhelper.findData(userEmail))
        }

        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@WorkSpaceListActivity, LinearLayoutManager.VERTICAL,false)

            adapter = WorkSpaceListAdpater(array)
            adapter.itemClickListener = object :WorkSpaceListAdpater.OnItemClickListener{
                override fun OnItemClick(holder: WorkSpaceListAdpater.MyViewHolder, view: View, data: User_WorkSpaceData, position: Int) {
                    val intent = Intent(this@WorkSpaceListActivity, DetailWorkspaceActivity::class.java)
                    intent.putExtra("workName", adapter.items[position].workSpaceName)
                    startActivity(intent)
                }
            }
            recyclerView.adapter = adapter


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