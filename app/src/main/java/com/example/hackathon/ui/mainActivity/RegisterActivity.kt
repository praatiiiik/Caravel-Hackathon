package com.example.hackathon.ui.mainActivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.hackathon.R
import com.example.hackathon.remote.firebase.FB
import com.example.hackathon.remote.firebase.FBFS
import com.example.hackathon.ui.dashboard.DashBoardActivity
import com.example.hackathon.util.Constants

class RegisterActivity : AppCompatActivity() {
    private lateinit var userNameET:TextView
    private lateinit var userAddressET:TextView
    private lateinit var userPhoneET:TextView
    private lateinit var emailET:TextView
    private lateinit var userPasswordET:TextView
    private lateinit var registerCardView: CardView
    private lateinit var registerPageProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initializeViews()
        registerCardView.setOnClickListener {
            if(isValidViews()){
                registerPageProgressBar.visibility = View.VISIBLE
                registerUser()
            }
        }
    }

    private fun registerUser() {
        val email = emailET.text.toString()
        val password = userPasswordET.text.toString()
        FB.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){
                saveUserData()
            }else{
                Log.d("register",it.exception.toString())
                showToast("Cannot Add User")
                showToast("Try Again")
            }
        }
    }

    private fun showToast(msg:String) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

    private fun saveUserData() {
        val db = FBFS.getInstance()
        val hm = HashMap<String, String>()
        hm[Constants.FBFS_USER_NAME] = userNameET.text.toString()
        hm[Constants.FBFS_USER_PHONE] = userPhoneET.text.toString()
        hm[Constants.FBFS_USER_ADDRESS] = userAddressET.text.toString()

        db.collection(getUSer()!!).document(Constants.FBFS_DOC_USER_DETAILS).set(hm).addOnCompleteListener {
            if (it.isSuccessful) {
                showToast("Registration Successful")
                registerPageProgressBar.visibility = View.INVISIBLE
                changeActivity(DashBoardActivity())
            } else {
                showToast("Error")
            }
        }
    }

    private fun changeActivity(activity: Activity) {
        startActivity(Intent(this,activity::class.java))
        finish()
    }

    private fun initializeViews() {
        userNameET = findViewById(R.id.hotelNameET)
        userPhoneET = findViewById(R.id.userPhoneET)
        userAddressET = findViewById(R.id.userAddressET)
        emailET = findViewById(R.id.emailET)
        userPasswordET = findViewById(R.id.userPasswordET)
        registerCardView = findViewById(R.id.registerCardView)
        registerPageProgressBar = findViewById(R.id.registerPageProgressBar)
    }

    private fun isValidViews():Boolean{
        var isValid = true
        userNameET.text.toString().apply {
            if(isEmpty()){
                isValid=false
                error("Enter Valid Name")
            }
        }
        userPhoneET.text.toString().apply {
            if(length!=10){
                isValid=false
                error("Enter Valid Phone Number")
            }
        }
        userAddressET.text.toString().apply {
            if(isEmpty()){
                isValid=false
                error("Enter Address")
            }
        }
        userPasswordET.text.toString().apply {
            if(length<6){
                isValid=false
                error("Enter 6 digit password")
            }
        }
        emailET.text.toString().apply {
            if(isEmpty() || !contains("@gmail.com")){
                isValid=false
                error("Enter must contains @gmail.com")
            }
        }
        return isValid
    }

    private fun getUSer() = FB.getInstance().currentUser?.email
}