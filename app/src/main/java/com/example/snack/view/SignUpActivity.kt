package com.example.snack.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.snack.DBHelper.UserDBHelper
import com.example.snack.R
import com.example.snack.data.UserIdData
import com.example.snack.dialog.LoadingDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
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
    lateinit var userDBHelper: UserDBHelper
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        firebaseAuth = FirebaseAuth.getInstance()
        init()
    }

    fun init(){
        userDBHelper = UserDBHelper(this)
        database = Firebase.database.reference
        editTextEmail = findViewById(R.id.et_email)
        editTextPassword = findViewById(R.id.et_password)
        editname=findViewById(R.id.edit_name)
        editphone=findViewById(R.id.edit_phone)
        val et_signupbtn =findViewById<TextView>(R.id.edit_signup)
        et_signupbtn.setOnClickListener {
            signUp()
        }


//        database.child("users").child("4ObTiSS9n2aZFRe4O57aiYoxqNG2").get().addOnSuccessListener {
//            Log.i("test",it.value.toString().trim())
//        }



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
                    Toast.makeText(this, "??????????????? 6????????? 16???????????? ?????????. ", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "????????? ????????? ????????? ?????????.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "?????? ?????? ??????????????????", Toast.LENGTH_SHORT).show()
        }
    }
    fun createUser(email: String, password: String){
        val dialog= LoadingDialog(this)
        dialog.show()
        firebaseAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val data=HashMap<String,String>()
                    data.put("name",name)
                    data.put("uid",firebaseAuth!!.uid.toString())
                    data.put("id",email.split("@")[0])
                    database.child("users").child(email.split("@")[0]).setValue(data)
                    database.child("users").child(firebaseAuth!!.uid.toString()).setValue(email.split("@")[0])
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this, "??????????????????.", Toast.LENGTH_SHORT).show();
                    dialog.dismiss()
                    userDBHelper.insertUser(UserIdData(0, email))
                    val intent= Intent(this,emailverify::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "????????? ????????? ?????????.", Toast.LENGTH_SHORT).show();
                    dialog.dismiss()
                }
            }

    }


    private fun isValidEmail(): Boolean {
        return if (email.isEmpty()) {
            // ????????? ??????
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // ????????? ?????? ?????????
            false
        } else {
            true
        }
    }

    // ???????????? ????????? ??????
    private fun isValidPasswd(): Boolean {
        return if (password.isEmpty()) {
            // ???????????? ??????
            false
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            // ???????????? ?????? ?????????
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