package com.example.hackathon.ui.dashboard.bottomNavFrags.addPlaceUI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.hackathon.R
import com.example.hackathon.model.AddHotel
import com.example.hackathon.remote.firebase.FB
import com.example.hackathon.remote.firebase.FBFS
import com.example.hackathon.ui.dashboard.DashBoardActivity
import com.example.hackathon.util.Constants
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.fragment_add_place.view.*


class AddPlaceFragment : Fragment() {
    private lateinit var hotelNameET: EditText
    private lateinit var roomsAvailable: EditText
    private lateinit var hotelAddress: EditText
    private lateinit var costPerNight: EditText
    private lateinit var description: EditText
    private lateinit var city: EditText
    private lateinit var ownerNumber: EditText
    private lateinit var ownerName: EditText
    private lateinit var addHotel: CardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_place, container, false)
        initializeViews(view)
        addHotel.setOnClickListener {
            checkFields()
        }
        return view
    }

    private fun checkFields() {
        if (isValidViews()) {
            val hotelName = hotelNameET.text.toString()
            val rooms = roomsAvailable.text.toString()
            val costPerNight = costPerNight.text.toString()
            val description = description.text.toString()
            val hotelAddress = hotelAddress.text.toString()
            val city = city.text.toString()
            val ownerName = ownerName.text.toString()
            val ownerNumber = ownerNumber.text.toString()
            val addHotel = AddHotel(
                hotelName,
                rooms,
                hotelAddress,
                costPerNight,
                description,
                ownerName,
                ownerNumber,
                city,
                getTimeStamp(),
                "0", "0"
            )
            addHotels(addHotel)
        }
    }

    private fun initializeViews(view: View) {
        hotelNameET = view.findViewById(R.id.hotelNameET)
        roomsAvailable = view.findViewById(R.id.roomsAvailable)
        hotelAddress = view.findViewById(R.id.hotelAddress)
        costPerNight = view.findViewById(R.id.costPerNight)
        description = view.findViewById(R.id.description)
        city = view.findViewById(R.id.city)
        ownerNumber = view.findViewById(R.id.ownerNumber)
        ownerName = view.findViewById(R.id.ownerName)
        addHotel = view.findViewById(R.id.addHotel)
    }

    private fun addHotels(hotel: AddHotel) {
        val db = FBFS.getInstance()
        val hm = HashMap<String, String>()
        hm["address"] = hotel.address
        hm["cost_per_night"] = hotel.cost_per_night
        hm["description"] = hotel.description
        hm["hotel_name"] = hotel.hotel_name
        hm["likes"] = hotel.likes
        hm["owner_city"] = hotel.owner_city
        hm["owner_name"] = hotel.owner_name
        hm["owner_no"] = hotel.owner_no
        hm["rooms_avail"] = hotel.rooms_avail
        hm["uniqueId"] = hotel.uniqueId
        hm["users"] = hotel.users
        db.collection(Constants.FBFS_ALL_CITIES).document(hotel.uniqueId)
            .set(hm)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast("Hotel Added Successfully")
                    clearEditTexts()
                } else {
                    showToast("Some Error Occurred")
                }
            }
    }

    private fun isValidViews() =
        !(hotelNameET.text.toString().isEmpty() || roomsAvailable.text.toString()
            .isEmpty() || hotelAddress.text.toString().isEmpty() || costPerNight.text.toString()
            .isEmpty() || description.text.toString().isEmpty() || city.text.toString()
            .isEmpty() || ownerNumber.text.toString().length != 10)

    private fun getUSer() = FB.getInstance().currentUser?.email

    private fun getTimeStamp() = System.currentTimeMillis().toString()

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    private fun clearEditTexts(){
        hotelNameET.text.clear()
        roomsAvailable.text.clear()
        costPerNight.text.clear()
        description.text.clear()
        hotelAddress.text.clear()
        city.text.clear()
        ownerName.text.clear()
        ownerNumber.text.clear()
    }
}



