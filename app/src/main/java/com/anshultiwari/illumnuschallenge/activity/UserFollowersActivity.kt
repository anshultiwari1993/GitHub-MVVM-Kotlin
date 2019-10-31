package com.anshultiwari.illumnuschallenge.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anshultiwari.illumnuschallenge.R
import com.anshultiwari.illumnuschallenge.adapter.FollowersAdapter
import com.anshultiwari.illumnuschallenge.model.Follower
import com.anshultiwari.illumnuschallenge.utilities.Util
import com.anshultiwari.illumnuschallenge.viewmodel.FollowersViewModel
import java.util.*

class UserFollowersActivity : AppCompatActivity() {
    private lateinit var followersRecyclerView: RecyclerView
    private lateinit var followersAdapter: FollowersAdapter
    private lateinit var followersList: List<Follower>
    private lateinit var toolbar: Toolbar

    private lateinit var followersViewModel: FollowersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_followers)

        followersList = ArrayList()

        // Setup views
        followersRecyclerView = findViewById(R.id.followers)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Get the ViewModel
        followersViewModel = ViewModelProviders.of(this).get(FollowersViewModel::class.java)

        // Get data from intent
        val login = intent.getStringExtra("login")!!
        supportActionBar?.title = "$login's Followers"

        // Check for internet and store
        if (!Util.isNetworkAvailable()) {
            Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show()

        } else {
            followersViewModel.fetchFollowersAndStore(login)
        }

        // Observe changes
        followersViewModel.getUserFollowers(login).observe(this, androidx.lifecycle.Observer { followers ->
            followersList = followers

            setupRecyclerView(followersList)
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }


    private fun setupRecyclerView(followersList: List<Follower>) {
        followersAdapter = FollowersAdapter(this, followersList)
        followersRecyclerView.adapter = followersAdapter
        followersRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        followersRecyclerView.setHasFixedSize(true)
    }


    companion object {
        private val TAG = "FollowersActivity"
    }
}
