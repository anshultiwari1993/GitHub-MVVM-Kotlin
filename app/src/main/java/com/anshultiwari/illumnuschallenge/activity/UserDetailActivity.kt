package com.anshultiwari.illumnuschallenge.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anshultiwari.illumnuschallenge.R
import com.anshultiwari.illumnuschallenge.adapter.ReposAdapter
import com.anshultiwari.illumnuschallenge.model.Repo
import com.anshultiwari.illumnuschallenge.model.UserDetail
import com.anshultiwari.illumnuschallenge.utilities.Util
import com.anshultiwari.illumnuschallenge.viewmodel.UserDetailsViewModel
import com.bumptech.glide.Glide

class UserDetailActivity : AppCompatActivity() {
    private lateinit var reposRecyclerView: RecyclerView
    private lateinit var reposAdapter: ReposAdapter
    private lateinit var repos: List<Repo>

    private lateinit var userDetailsViewModel: UserDetailsViewModel

    private lateinit var nameTextView: TextView
    private lateinit var companyTextView: TextView
    private lateinit var avatarImageView: ImageView
    private lateinit var followersTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        // Setup Views
        reposRecyclerView = findViewById(R.id.repos)
        nameTextView = findViewById(R.id.name)
        companyTextView = findViewById(R.id.company)
        avatarImageView = findViewById(R.id.avatar)
        followersTextView = findViewById(R.id.followers)

        // Get the ViewModel
        userDetailsViewModel = ViewModelProviders.of(this).get(UserDetailsViewModel::class.java)

        // Check for internet
        if (!Util.isNetworkAvailable()) {
            Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show()

        } else {
            val login = intent.getStringExtra("login")!!
            userDetailsViewModel.fetchUserDetailsAndStore(login)
            userDetailsViewModel.fetchUserReposAndStore(login)
        }

        val userId = intent.getLongExtra("user_id", -1)
        val userLogin = intent.getStringExtra("login")

        // Observe Changes
        userDetailsViewModel.getUserDetails(userId).observe(this, Observer { userDetails ->
            inflateViewWithUserDetails(userDetails)
        })

        userDetailsViewModel.getUserRepos(userId).observe(this, Observer { repos ->
            setupRecyclerView(repos)
        })


        // Set listeners on views
        followersTextView.setOnClickListener(View.OnClickListener {view ->

            val intent = Intent(this, UserFollowersActivity::class.java)
            intent.putExtra("user_id", userId)
            intent.putExtra("login", userLogin)

            startActivity(intent)
        })

    }

    private fun inflateViewWithUserDetails(userDetails: UserDetail?) {
        val name = userDetails?.name ?: ""
        val company = userDetails?.company ?: ""

        if (!name.equals("null")) {
            nameTextView.text = name
        }

        if (!company.equals("null")) {
            companyTextView.text = company
        }

        followersTextView.text  = userDetails?.followers.toString()

        Glide.with(this).load(userDetails?.avatarUrl).into(avatarImageView)

    }

    private fun setupRecyclerView(repos: List<Repo>) {
        reposAdapter = ReposAdapter(this, repos)
        reposRecyclerView.adapter = reposAdapter
        reposRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        reposRecyclerView.setHasFixedSize(true)
    }

    companion object {
        private val TAG = "UserDetailActivity"
    }
}
