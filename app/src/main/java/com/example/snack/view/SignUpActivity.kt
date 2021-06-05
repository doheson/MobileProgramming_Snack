package com.example.snack.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.snack.R
import com.example.snack.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {

    private val PASSWORD_PATTERN: Pattern = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{6,16}$")
    private var email = ""
    private var password = ""
    private var name = ""
    private var phone = ""
    var editTextEmail: EditText? = null
    var editTextPassword: EditText? = null
    var editphone: EditText? = null
    var editname: EditText? = null
    private var firebaseAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        firebaseAuth = FirebaseAuth.getInstance()
        init()
    }

    fun init(){
        editTextEmail = findViewById(R.id.et_email)
        editTextPassword = findViewById(R.id.et_password)
        editname=findViewById(R.id.edit_name)
        editphone=findViewById(R.id.edit_phone)
        val et_signupbtn =findViewById<TextView>(R.id.edit_signup)
        et_signupbtn.setOnClickListener {
            signUp()
        }

    }

    fun signUp() {
        email = editTextEmail!!.text.toString()
        password = editTextPassword!!.text.toString()
        name = editname!!.text.toString()
        phone = editphone!!.text.toString()

        if (isEmpty(email) && isEmpty(password) && isEmpty(name) && isEmpty(phone)) {
            if (isValidEmail()) {
                if (isValidPasswd())
                    createUser(email, password)
                else
                    Toast.makeText(this, "비밀번호는 6자이상 16자리이하 입니다. ", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "이메일 형식을 따라야 합니다.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "모든 칸을 기입해주세요", Toast.LENGTH_SHORT).show()
        }
    }
    fun createUser(email: String, password: String){
        val dialog=LoadingDialog(this)
        dialog.show()
        firebaseAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this, "회원가입성공.", Toast.LENGTH_SHORT).show();
                    dialog.dismiss()
                    val intent= Intent(this,emailverify::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "중복된 아이디 입니다.", Toast.LENGTH_SHORT).show();
                    dialog.dismiss()
                }
            }

    }


    private fun isValidEmail(): Boolean {
        return if (email.isEmpty()) {
            // 이메일 공백
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // 이메일 형식 불일치
            false
        } else {
            true
        }
    }

    // 비밀번호 유효성 검사
    private fun isValidPasswd(): Boolean {
        return if (password.isEmpty()) {
            // 비밀번호 공백
            false
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            // 비밀번호 형식 불일치
            false
        } else {
            true
        }
    }

    private fun isEmpty(s: String): Boolean {
        return if (s.isEmpty()) {
            false
        } else true
    }

}