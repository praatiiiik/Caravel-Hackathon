package com.example.hackathon.ui.dashboard.bottomNavFrags.exploreUI

import androidx.lifecycle.LiveData
import com.example.hackathon.model.HotelList
import com.example.hackathon.remote.server.Networking

class GetRoomsRepo {
    fun fetchHotelList(city:String):List<HotelList>?{
        val response = Networking.server().getHotels(city)
        return if(response.isSuccessful && response.body()!=null){
            response.body()
        }else{
            null
        }
    }

    fun fetchHotelData(uniqueId:String){

    }
}