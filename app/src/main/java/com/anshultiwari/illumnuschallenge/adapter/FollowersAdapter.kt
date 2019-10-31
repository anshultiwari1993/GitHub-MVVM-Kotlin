package com.anshultiwari.illumnuschallenge.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anshultiwari.illumnuschallenge.R
import com.anshultiwari.illumnuschallenge.model.Follower
import com.bumptech.glide.Glide

class FollowersAdapter(private val context: Context, private val followers: List<Follower>) : RecyclerView.Adapter<FollowersAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.follower_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val follower = followers[position]

        val login = follower.login

        if (!login.equals("null")) {
            holder.loginTextView.text = login
        }

        Glide.with(context).load(follower.avatarUrl).into(holder.avatarImageView)

    }

    override fun getItemCount(): Int {
        return followers.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val loginTextView: TextView = view.findViewById(R.id.login)
        val avatarImageView: ImageView = view.findViewById(R.id.avatar)
    }

    companion object {
        private val TAG = "FollowersAdapter"
    }

}
