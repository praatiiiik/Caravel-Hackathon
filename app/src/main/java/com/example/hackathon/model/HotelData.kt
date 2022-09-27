package com.example.hackathon.model

import android.system.StructTimespec
import androidx.room.Entity
import androidx.room.PrimaryKey

data class HotelData(
    val hotel_name: String,
    val rooms_avail: Int,
    val address: String,
    val cost_per_night: Int,
    val description : String,
    val features:String,
    val review:Int,
    val owner_detail:String
)
