package com.example.hackathon.ui.dashboard.bottomNavFrags.exploreUI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hackathon.R
import com.example.hackathon.model.AddHotel
import com.example.hackathon.ui.hoteldetails.HotelDetails
import com.example.hackathon.util.Constants
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot


class ExplorePlaceFragment : Fragment() {
    private lateinit var hotelListRV: RecyclerView
    private lateinit var exploreRVAdapter: ExploreRVAdapter
    private lateinit var citySpinner: Spinner
    private var cityName ="delhi"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_explore_place, container, false)
        initializeSpinner(view)
        setUpRecyclerView(view)
        return view
    }

    private fun getData() {
        val ref = FirebaseFirestore.getInstance().collection(Constants.FBFS_ALL_CITIES)
        ref.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val hotelList = ArrayList<AddHotel>()
                for(hotels in it.result){
                    val hotel = getAddHotel(hotels)
                    if(hotel.owner_city==cityName)hotelList.add(hotel)
                }
                exploreRVAdapter.submitList(hotelList.toMutableList())
            } else {
                Log.d("dataFBFS", it.exception?.message.toString())
            }
        }
    }

    private fun getAddHotel(hotels: QueryDocumentSnapshot?):AddHotel {
        return AddHotel(
            hotels?.get("hotel_name").toString(),
            hotels?.get("rooms_avail").toString(),
            hotels?.get("address").toString(),
            hotels?.get("cost_per_night").toString(),
            hotels?.get("description").toString(),
            hotels?.get("owner_name").toString(),
            hotels?.get("owner_no").toString(),
            hotels?.get("owner_city").toString(),
            hotels?.get("uniqueId").toString(),
            hotels?.get("rooms_avail").toString(),
            hotels?.get("rooms_avail").toString(),
        )
    }


    private fun getQueriedData(city: String) {
        FirebaseFirestore.getInstance().collection(Constants.FBFS_ALL_CITIES)
            .whereEqualTo("owner_city", "delhi").get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("dataFBFS", it.result.documents.toString())
                    val hotelList = ArrayList<AddHotel>()
                    for (hotels in it.result) {
                        hotelList.add(hotels.toObject(AddHotel::class.java))
                    }
                    exploreRVAdapter.submitList(hotelList.toMutableList())
                } else {
                    Log.d("dataFBFS", it.exception?.message.toString())
                }
            }
    }

    private fun initializeSpinner(view: View) {
        citySpinner = view.findViewById(R.id.citySpinner)
        citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                //getQueriedData(p0?.getItemAtPosition(p2).toString().lowercase())
                cityName = p0?.getItemAtPosition(p2).toString().lowercase()
                getData()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    private fun setUpRecyclerView(view: View) {
        hotelListRV = view.findViewById(R.id.hotelListRV)
        exploreRVAdapter =
            ExploreRVAdapter(requireContext(), this@ExplorePlaceFragment::itemClicked)
        hotelListRV.apply {
            this.layoutManager = LinearLayoutManager(requireContext())
            this.adapter = exploreRVAdapter
        }
    }

    private fun itemClicked(uniqueId: String) {
        val intent = Intent(requireContext(),HotelDetails::class.java)
        intent.putExtra(Constants.HOTEL_DOC,uniqueId)
        startActivity(intent)
    }

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

}