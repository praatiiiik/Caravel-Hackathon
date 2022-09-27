package com.example.hackathon.ui.dashboard.bottomNavFrags.exploreUI

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hackathon.R
import com.example.hackathon.model.AddHotel

class ExploreRVAdapter(
    private val context: Context,
    private val onItemClicked: (String) -> Unit
) : ListAdapter<AddHotel, ExploreRVAdapter.ListViewHolder>(DiffUtil()) {

    class ListViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val name: TextView = view.findViewById(R.id.hotelNameTV)
        val cost: TextView = view.findViewById(R.id.hotelRentTV)
        val rating: TextView = view.findViewById(R.id.textView2)
        val hotelCardView: CardView = view.findViewById(R.id.hotelCardView)
        fun bind(data: AddHotel, onItemClicked: (String) -> Unit){
            name.text=data.hotel_name
            cost.text=data.cost_per_night
            rating.text=data.likes
            hotelCardView.setOnClickListener {
                onItemClicked(data.uniqueId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hotellist_rv,parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = getItem(position)
        Log.d("listData",item.toString())
        holder.bind(item,onItemClicked)
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<AddHotel>() {
        override fun areItemsTheSame(oldItem: AddHotel, newItem: AddHotel): Boolean {
            return oldItem== newItem
        }

        override fun areContentsTheSame(oldItem: AddHotel, newItem: AddHotel): Boolean {
            return oldItem == newItem
        }
    }

}