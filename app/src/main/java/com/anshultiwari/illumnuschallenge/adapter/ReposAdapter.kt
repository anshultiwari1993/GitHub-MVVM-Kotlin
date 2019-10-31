package com.anshultiwari.illumnuschallenge.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anshultiwari.illumnuschallenge.R
import com.anshultiwari.illumnuschallenge.model.Repo

class ReposAdapter(private val context: Context, private val repos: List<Repo>) : RecyclerView.Adapter<ReposAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.repo_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val repo = repos[position]

        val name = repo.name
        val description = repo.description
        val url = repo.url

        if (name != "null") {
            holder.nameTextView.text = name
        }

        if (description != "null") {
            holder.descriptionTextView.text = description
        }

        if (url != "null") {
            holder.urlTextView.text = url
        }

    }

    override fun getItemCount(): Int {
        return repos.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val nameTextView: TextView = view.findViewById(R.id.name)
        val urlTextView: TextView = view.findViewById(R.id.url)
        val descriptionTextView: TextView = view.findViewById(R.id.description)

        init {
            view.setOnClickListener(this)
        }


        override fun onClick(p0: View?) {
            val pos = adapterPosition

        }

    }

    companion object {
        private val TAG = "ReposAdapter"
    }

}
