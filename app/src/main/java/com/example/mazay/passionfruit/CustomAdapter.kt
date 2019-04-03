package com.example.mazay.passionfruit

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mazay.passionfruit.models.ProfileResponse


    class CustomAdapter(val userList: List<ProfileResponse.User>?, val clickListener: (ProfileResponse.User) -> Unit): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
            val v = LayoutInflater.from(p0?.context).inflate(R.layout.list_layout, p0, false)
            return ViewHolder(v)
        }

        override fun getItemCount(): Int {
            return userList?.size!!
        }

        override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
            val user: ProfileResponse.User = userList!![p1]

            p0.itemView.setOnClickListener { clickListener(user) }
            p0.textViewName?.text = user.name
            //p0.textViewTime?.text = stop.arvTime
        }

        class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val textViewName = itemView.findViewById<TextView>(R.id.textViewName)
            val textViewTime = itemView.findViewById<TextView>(R.id.textViewMajor)
            val textViewYear = itemView.findViewById<TextView>(R.id.textViewYear)
        }
    }
