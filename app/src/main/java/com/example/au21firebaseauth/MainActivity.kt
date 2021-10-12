package com.example.au21firebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    val TAG = "!!!"

    lateinit var auth : FirebaseAuth
    lateinit var textEmail : EditText
    lateinit var textPassword : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        textEmail = findViewById(R.id.editEmail)
        textPassword = findViewById(R.id.editPassword)

        val createButton = findViewById<Button>(R.id.buttonCreate)
        createButton.setOnClickListener(::creatUser)

        val loginButton = findViewById<Button>(R.id.buttonLogin)
        loginButton.setOnClickListener {
           loginUser()
        }

        goToAddActivity()
    }

    fun goToAddActivity() {
        val intent = Intent(this , AddItemActivity::class.java)
        startActivity(intent)
    }

    fun loginUser() {
        val email = textEmail.text.toString()
        val password = textPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener  { task ->
                if ( task.isSuccessful) {
                    Log.d(TAG, "loginUser: Success")
                    goToAddActivity()
                } else {
                    Log.d(TAG, "loginUser: user not loged in ${task.exception}")
                }
            }
    }

    fun creatUser(view : View) {
        val email = textEmail.text.toString()
        val password = textPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener  { task ->
                if ( task.isSuccessful) {
                    Log.d(TAG, "creatUser: Success")
                    goToAddActivity()
                } else {
                    Log.d(TAG, "creatUser: user not created ${task.exception}")
                }
            }
    }
}