package com.example.snack.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import com.example.snack.R
import com.example.snack.databinding.ActivityMainBinding
import com.example.snack.dialog.LoadingDialog
import com.example.snack.dialog.NoticeDialog
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val firebaseAuth: FirebaseAuth? = FirebaseAuth.getInstance()
    var signEmail: EditText? = null
    var signPassword: EditText? = null
    private var email = ""
    private var password = ""
    private val PASSWORD_PATTERN: Pattern = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{6,16}$")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init(){

        if (firebaseAuth!!.currentUser?.isEmailVerified() == true) {
            val intent = Intent(this, WorkSpaceListActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        signEmail=findViewById(R.id.id)
        signPassword = findViewById(R.id.password)
        binding.apply {
            signUp.setOnClickListener {
                val intent = Intent(this@MainActivity, SignUpActivity::class.java)
                startActivity(intent)
            }
            login.setOnClickListener {
                signIn()
            }
            repwbt.setOnClickListener {
                repw()
            }
            noticeButton.setOnClickListener {
                val dialog = NoticeDialog(this@MainActivity)
                dialog.show()
            }
        }
    }
    fun signIn(){
        email=signEmail?.text.toString()
        password = signPassword?.getText().toString()
        if(isVaildEmail()&&isVaildPasswd()) {
            loginuser(email, password)
        }
    }
    private fun loginuser(email: String, password: String){
        val dialog= LoadingDialog(this)
        dialog.show()
        if (firebaseAuth != null) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        if(firebaseAuth.currentUser!!.isEmailVerified())
                        {dialog.dismiss()
                            val intent= Intent(this, WorkSpaceListActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)}
                        else{
                            dialog.dismiss()
                            val intent=Intent(this,emailverify::class.java)
                            startActivity(intent)
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        dialog.dismiss()
                        Toast.makeText(this, "로그인에 실패", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun isVaildEmail():Boolean{
        if (email.isEmpty()) {
            // 이메일 공백
            Toast.makeText(this, "이메일 칸을 기입해주세요", Toast.LENGTH_SHORT).show()
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // 이메일 형식 불일치
            Toast.makeText(this, "이메일 형식을 따라야 합니다.", Toast.LENGTH_SHORT).show()
            return false;
        } else {
            return true;
        }
    }
    private fun isVaildPasswd():Boolean{
        if (password.isEmpty()) {
            // 비밀번호 공백
            Toast.makeText(this, "비밀번호 칸을 기입해주세요 ", Toast.LENGTH_SHORT).show()
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            // 비밀번호 형식 불일치
            Toast.makeText(this, "비밀번호는 6자이상 16자리이하 입니다. ", Toast.LENGTH_SHORT).show()
            return false;
        } else {
            return true;
        }
    }

    fun repw(){
        email=signEmail?.text.toString()
        if(isVaildEmail()){
            firebaseAuth?.sendPasswordResetEmail(email)
            Toast.makeText(this, "설정하신 이메일로 비밀번호 재설정메일을 발신하였습니다.", Toast.LENGTH_SHORT).show()
        }
    }

}