package com.example.snack.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.snack.DBHelper.UserDBHelper
import com.example.snack.DBHelper.User_WorkSpaceDBHelper
import com.example.snack.adapter.AddUserAdapter
import com.example.snack.adapter.WorkSpaceAddAdapter
import com.example.snack.data.UserIdData
import com.example.snack.data.User_WorkSpaceData
import com.example.snack.databinding.ActivityAddMemberBinding

class AddMemberActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddMemberBinding

    lateinit var userDBHelper: UserDBHelper
    lateinit var adapter: AddUserAdapter
    var array = ArrayList<UserIdData>()

    lateinit var adapter2: WorkSpaceAddAdapter
    var array2 = ArrayList<String>()

    var workName = ""
    lateinit var userWorkspacedbhelper: User_WorkSpaceDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init(){
        userDBHelper = UserDBHelper(this@AddMemberActivity)
        userWorkspacedbhelper = User_WorkSpaceDBHelper(this)

        workName = intent.getStringExtra("workName").toString()

        binding.apply {
            //유저 검색 결과 리사이클러뷰
            recyclerView.layoutManager = LinearLayoutManager(this@AddMemberActivity, LinearLayoutManager.VERTICAL,false)

            adapter = AddUserAdapter(array)
            adapter.itemClickListener = object :AddUserAdapter.OnItemClickListener{
                override fun OnItemClick(holder: AddUserAdapter.MyViewHolder, view: View, data: UserIdData, position: Int) {
                    //팀원으로 추가하기
                    adapter2.addChannel(data.userId)
                }

            }
            recyclerView.adapter = adapter

            //내 팀원 리사이클러뷰
            recyclerView2.layoutManager = LinearLayoutManager(this@AddMemberActivity, LinearLayoutManager.VERTICAL,false)

            adapter2 = WorkSpaceAddAdapter(array2)
            adapter2.itemClickListener = object :WorkSpaceAddAdapter.OnItemClickListener{
                override fun OnItemClick(holder: WorkSpaceAddAdapter.MyViewHolder, view: View, data: String, position: Int) {
                    //팀원에서 삭제하기
                    adapter2.deleteChannel(position)
                }
            }
            recyclerView2.adapter = adapter2

            //버튼 이벤트
            searchbtn.setOnClickListener {
                adapter.addData(userDBHelper.findProduct2(findUser.text.toString()))
                Log.e("유저 찾기", findUser.text.toString())
            }

            addWorkspace.setOnClickListener {//팀원 추가하기
                for(i in 0..adapter2.items.size){
                    userWorkspacedbhelper.insertData(User_WorkSpaceData(adapter2.items[i], workName)) //에러
                }
                val intent = Intent(this@AddMemberActivity, WorkSpaceListActivity::class.java)
                startActivity(intent)
            }
        }
    }
}