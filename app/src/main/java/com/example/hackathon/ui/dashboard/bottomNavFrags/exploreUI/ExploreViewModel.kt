package com.example.hackathon.ui.dashboard.bottomNavFrags.exploreUI

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackathon.model.HotelList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExploreViewModel:ViewModel() {
    private val _hotels : MutableLiveData<List<HotelList>> = MutableLiveData()
    val movies : LiveData<List<HotelList>> = _hotels

    fun getMovies(){
        val getRoomsRepo = GetRoomsRepo()
        viewModelScope.launch(Dispatchers.IO) {
            _hotels.value = getRoomsRepo.fetchHotelList("1")
        }
    }
}