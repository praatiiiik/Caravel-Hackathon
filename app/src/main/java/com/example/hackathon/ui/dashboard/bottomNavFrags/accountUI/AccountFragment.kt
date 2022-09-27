package com.example.hackathon.ui.dashboard.bottomNavFrags.accountUI

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.hackathon.R
import com.example.hackathon.remote.firebase.FB
import com.example.hackathon.remote.firebase.FBFS
import com.example.hackathon.ui.mainActivity.MainActivity
import com.example.hackathon.util.Constants
import com.google.firebase.firestore.DocumentSnapshot


class AccountFragment : Fragment() {

    private lateinit var userNameTV : TextView
    private lateinit var userPhoneTV : TextView
    private lateinit var userAddressTV : TextView
    private lateinit var roomBooked : TextView
    private lateinit var profileProgressBar : ProgressBar
    private lateinit var logOutCardView: CardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)
        initializeViews(view)
        initializeClickListeners()
        return view
    }

    private fun initializeViews(view : View) {
        userNameTV = view.findViewById(R.id.userNameTV)
        userPhoneTV = view.findViewById(R.id.userPhoneTV)
        userAddressTV = view.findViewById(R.id.userAddressTV)
        roomBooked = view.findViewById(R.id.roomBooked)
        logOutCardView = view.findViewById(R.id.logoutCardView)
        profileProgressBar = view.findViewById(R.id.profileProgressBar)
    }

    private fun initializeClickListeners(){
        logOutCardView.setOnClickListener {
            logOut()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
    }

    private fun getData() {
        val ref = FBFS.getInstance().collection(getUSer()!!).document(Constants.FBFS_DOC_USER_DETAILS)
        ref.get().addOnCompleteListener {
            profileProgressBar.visibility = View.INVISIBLE
            if (it.isSuccessful) {
                val data = it.result
                if (data.exists()) {
                    setData(data)
                } else {
                    showToast("No Data Exist")
                }
            } else {
                showToast("Some thing Gone Wrong")
            }
        }
    }

    private fun setData(data : DocumentSnapshot){
        userNameTV.text = data[Constants.FBFS_USER_NAME].toString()
        userPhoneTV.text = data[Constants.FBFS_USER_PHONE].toString()
        userAddressTV.text = data[Constants.FBFS_USER_ADDRESS].toString()
        val room = data[Constants.BOOKED_ROOM].toString()
        if(room.isNotEmpty()){
            userAddressTV.text = "Booked $room"
        }
    }

    private fun getUSer() = FB.getInstance().currentUser?.email

    private fun showToast(msg:String) {
        Toast.makeText(requireContext(),msg, Toast.LENGTH_LONG).show()
    }

    private fun logOut(){
        FB.getInstance().signOut()
        changeActivity(MainActivity())
    }

    private fun changeActivity(activity: Activity){
        startActivity(Intent(requireActivity(),activity::class.java))
        requireActivity().finish()
    }
}