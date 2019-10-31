package com.anshultiwari.illumnuschallenge.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anshultiwari.illumnuschallenge.R
import com.anshultiwari.illumnuschallenge.activity.UserDetailActivity
import com.anshultiwari.illumnuschallenge.model.User
import com.bumptech.glide.Glide

class UsersAdapter(private val context: Context, private val usersList: List<User>) : RecyclerView.Adapter<UsersAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = usersList[position]

        holder.loginTextView.text = user.login
        Glide.with(context).load(user.avatarUrl).into(holder.avatarImageView)

    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val loginTextView: TextView = view.findViewById(R.id.user_login)
        val avatarImageView: ImageView = view.findViewById(R.id.user_avatar)

        init {
            view.setOnClickListener(this)
        }


        override fun onClick(p0: View?) {
            val pos = adapterPosition

            val userId = usersList[pos].id
            val userLogin = usersList[pos].login

            val intent = Intent(context, UserDetailActivity::class.java)
            intent.putExtra("user_id", userId)
            intent.putExtra("login", userLogin)

            context.startActivity(intent)
        }

    }

    companion object {
        private val TAG = "CelebsAdapter"
    }

}
