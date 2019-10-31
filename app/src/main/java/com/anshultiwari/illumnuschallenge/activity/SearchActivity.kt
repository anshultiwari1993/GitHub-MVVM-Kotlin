package com.anshultiwari.illumnuschallenge.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anshultiwari.illumnuschallenge.adapter.UsersAdapter
import com.anshultiwari.illumnuschallenge.model.User
import com.anshultiwari.illumnuschallenge.utilities.Util
import com.anshultiwari.illumnuschallenge.viewmodel.SearchViewModel
import java.util.*


class SearchActivity : AppCompatActivity() {
    private lateinit var searchEditText: EditText
    private lateinit var usersRecyclerView: RecyclerView
    private lateinit var usersAdapter: UsersAdapter
    private lateinit var usersList: List<User>

    private lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.anshultiwari.illumnuschallenge.R.layout.activity_search)

        usersList = ArrayList()

        // Setup views
        usersRecyclerView = findViewById(com.anshultiwari.illumnuschallenge.R.id.users)
        searchEditText = findViewById(com.anshultiwari.illumnuschallenge.R.id.search)

        // Get the ViewModel
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)


        // Observe changes
        searchViewModel.getSearchedUsers().observe(this, Observer { searchedUsers ->
            setupRecyclerView(searchedUsers)
        })

        // Set listeners on views
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Check for internet and fetch
                fetchUsers(p0)

            }
        })

        // Search for the users on pressing enter
        searchEditText.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                // If the event is a key-down event on the "enter" button
                if (event.getAction() === KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    // Perform action on key press
                    val query = searchEditText.text.toString()
                    fetchUsers(query)
                    return true
                }
                return false
            }
        })
    }

    private fun fetchUsers(query: CharSequence?) {
        if (!Util.isNetworkAvailable()) {
            Toast.makeText(this@SearchActivity, "No internet", Toast.LENGTH_SHORT).show()

        } else {
            searchViewModel.fetchSearchedUsers(query.toString())
        }
    }

    private fun setupRecyclerView(usersList: List<User>) {
        usersAdapter = UsersAdapter(this, usersList)
        usersRecyclerView.adapter = usersAdapter
        usersRecyclerView.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        usersRecyclerView.setHasFixedSize(true)
    }
}
