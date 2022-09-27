package com.example.hackathon.remote.server

import com.example.hackathon.model.HotelList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {
    @GET("stayzyinfo/")
    fun getHotels(@Query("city") city:String):Response<List<HotelList>>
}