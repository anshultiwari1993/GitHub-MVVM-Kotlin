package com.anshultiwari.illumnuschallenge.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anshultiwari.illumnuschallenge.R
import com.anshultiwari.illumnuschallenge.adapter.UsersAdapter
import com.anshultiwari.illumnuschallenge.model.User
import com.anshultiwari.illumnuschallenge.utilities.Util
import com.anshultiwari.illumnuschallenge.viewmodel.UsersViewModel
import java.util.*

class UsersActivity : AppCompatActivity() {
    private lateinit var usersRecyclerView: RecyclerView
    private lateinit var usersAdapter: UsersAdapter
    private lateinit var usersList: List<User>
    private lateinit var toolbar: Toolbar

    private lateinit var usersViewModel: UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        usersList = ArrayList()

        // Setup views
        usersRecyclerView = findViewById(R.id.users_rv)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Get the ViewModel
        usersViewModel = ViewModelProviders.of(this).get(UsersViewModel::class.java)


        // Check for internet and store
        if (!Util.isNetworkAvailable()) {
            Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show()

        } else {
            usersViewModel.fetchUsersAndStore()
        }


        // Observe changes
        usersViewModel.getAllUsers().observe(this, androidx.lifecycle.Observer { users ->
            Log.d(TAG, "onChanged: called")
            Log.d(TAG, "onChanged: users size = " + users.size)
            usersList = users

            setupRecyclerView(users)
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.users_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> {
                startSearchActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun startSearchActivity() {
        startActivity(Intent(this, SearchActivity::class.java))
    }

    private fun setupRecyclerView(usersList: List<User>) {
        usersAdapter = UsersAdapter(this, usersList)
        usersRecyclerView.adapter = usersAdapter
        usersRecyclerView.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        usersRecyclerView.setHasFixedSize(true)
    }

    companion object {
        private val TAG = "UsersActivity"
    }
}
