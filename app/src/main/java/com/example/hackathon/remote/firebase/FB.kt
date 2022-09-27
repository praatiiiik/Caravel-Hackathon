package com.example.hackathon.remote.firebase

import android.app.Activity
import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

object FB {
    fun getInstance() = FirebaseAuth.getInstance()
}
object FBFS{
    fun getInstance() = FirebaseFirestore.getInstance()
}