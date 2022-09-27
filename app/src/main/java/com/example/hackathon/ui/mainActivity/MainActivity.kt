package com.example.hackathon.ui.mainActivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.hackathon.R
import com.example.hackathon.remote.firebase.FB
import com.example.hackathon.ui.dashboard.DashBoardActivity

class MainActivity : AppCompatActivity() {

    private lateinit var userNameET: EditText
    private lateinit var userPasswordET: EditText
    private lateinit var loginButton: CardView
    private lateinit var registerHere: TextView
    private lateinit var loginTextView: TextView
    private lateinit var loginPageProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeViews()

        registerHere.setOnClickListener {
            changeActivity(RegisterActivity())
        }

        loginButton.setOnClickListener {
            if (isValidPassword() && isValidUserName()) {
                loginPageProgressBar.visibility = View.VISIBLE
                val userName = userNameET.text.toString()
                val userPassword = userPasswordET.text.toString()
                login(userName, userPassword)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (FB.getInstance().currentUser != null) {
            changeActivity(DashBoardActivity())
        }
    }

    private fun initializeViews() {
        supportActionBar?.hide()
        userNameET = findViewById(R.id.hotelNameET)
        userPasswordET = findViewById(R.id.userPasswordET)
        loginButton = findViewById(R.id.loginButton)
        registerHere = findViewById(R.id.registerHere)
        loginTextView = findViewById(R.id.loginTextView)
        loginPageProgressBar = findViewById(R.id.loginPageProgressBar)
    }

    private fun login(email: String, password: String) {
        FB.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {
            loginPageProgressBar.visibility = View.INVISIBLE
            if (it.isSuccessful) {
                changeActivity(DashBoardActivity())
            } else {
                showToast("Cannot Login")
                showToast("Try Again")
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun changeActivity(activity: Activity) {
        startActivity(Intent(this, activity::class.java))
        finish()
    }

    private fun isValidUserName() = !userNameET.text.toString().isEmpty()
    private fun isValidPassword(): Boolean {
        return if (userPasswordET.text.toString().length > 5) true
        else {
            userPasswordET.error = "Password Must be of minimum 6 chars"
            false
        }
    }

}

//binding = ActivityDashboardBinding.inflate(layoutInflater)
//        setContentView(binding.root)