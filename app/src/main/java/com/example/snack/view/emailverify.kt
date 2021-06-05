package com.example.snack.view

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.snack.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class emailverify : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emailverify)
        val showemail=findViewById<TextView>(R.id.showemail)
        val firebaseAuthuser= Firebase.auth.currentUser
        showemail.setText(firebaseAuthuser?.email)
        val sendemail=findViewById<Button>(R.id.sendemail)
        sendemail.setOnClickListener {
            firebaseAuthuser!!.sendEmailVerification()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(getApplicationContext(), "이메일 송신 완료", Toast.LENGTH_LONG).show()
                        firebaseAuthuser.reload()
                    }
                }
        }

        val checkverify=findViewById<Button>(R.id.checkverify)
        checkverify.setOnClickListener {
            firebaseAuthuser!!.reload().addOnSuccessListener {
                if(firebaseAuthuser!!.isEmailVerified){
                    val intent= Intent(this,WorkSpaceListActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(getApplicationContext(), "이메일 인증 완료후 클릭해주세요", Toast.LENGTH_LONG).show()
                }
            }

        }
    }

    override fun onPause() {
        super.onPause()
        if(Firebase.auth.currentUser!!.isEmailVerified)
            Firebase.auth.signOut()
    }
}