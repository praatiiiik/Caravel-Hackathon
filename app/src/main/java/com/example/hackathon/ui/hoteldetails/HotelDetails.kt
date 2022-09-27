package com.example.hackathon.ui.hoteldetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.hackathon.R
import com.example.hackathon.remote.firebase.FB
import com.example.hackathon.remote.firebase.FBFS
import com.example.hackathon.util.Constants
import com.google.firebase.firestore.FirebaseFirestore

class HotelDetails : AppCompatActivity() {
    private lateinit var hotelName: TextView
    private lateinit var descTV: TextView
    private lateinit var ratingTV: TextView
    private lateinit var ownerName: TextView
    private lateinit var textView3: TextView
    private lateinit var addHotel: CardView
    var rooms = 0
    var bookedHotelRoom = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel_details)
        setUpView()
        val doc = intent.getStringExtra(Constants.HOTEL_DOC)
        if(doc!=null) getData(doc)

        addHotel.setOnClickListener {
            if(rooms<1){
                textView3.text = "No Rooms Available"
            }else{
                bookRoom(doc!!)
            }
        }
    }

    private fun bookRoom(doc: String) {
        FBFS.getInstance().collection(Constants.FBFS_ALL_CITIES).document(doc).update("rooms_avail",rooms-1).addOnCompleteListener {
            if(it.isSuccessful){
                showToast("Room Booked")
                addRoomToProfile()
            }else{
                showToast("Some Error Occurred")
            }
        }
    }

    private fun addRoomToProfile() {
        FBFS.getInstance().collection(getUSer()!!).document(Constants.FBFS_DOC_USER_DETAILS).update(Constants.BOOKED_ROOM,bookedHotelRoom)
        finish()
    }

    private fun getUSer() = FB.getInstance().currentUser?.email

    private fun getData(doc: String) {
        FBFS.getInstance().collection(Constants.FBFS_ALL_CITIES).document(doc).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    bookedHotelRoom = it.result["hotel_name"].toString()
                    hotelName.text = bookedHotelRoom
                    descTV.text = it.result["description"].toString()
                    ratingTV.text = it.result["likes"].toString()
                    ownerName.text = it.result["owner_name"].toString()
                    textView3.text = "Rooms Available : ${it.result["rooms_avail"]}"
                    rooms=it.result["rooms_avail"].toString().toInt()
                }
            }
    }

    private fun setUpView() {
        supportActionBar?.hide()
        hotelName = findViewById(R.id.hotelName)
        descTV = findViewById(R.id.descTV)
        ratingTV = findViewById(R.id.ratingTV)
        ownerName = findViewById(R.id.ownerName)
        textView3 = findViewById(R.id.textView3)
        addHotel = findViewById(R.id.addHotel)
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}