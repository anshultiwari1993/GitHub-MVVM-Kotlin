package com.anshultiwari.illumnuschallenge.repo

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.anshultiwari.illumnuschallenge.MyVolley
import com.anshultiwari.illumnuschallenge.database.*
import com.anshultiwari.illumnuschallenge.model.Follower
import com.anshultiwari.illumnuschallenge.model.Repo
import com.anshultiwari.illumnuschallenge.model.User
import com.anshultiwari.illumnuschallenge.model.UserDetail
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class UserRepository(application: Application) {

    private val userDao: UserDao
    private val userDetailDao: UserDetailDao
    private val userRepoDao: RepoDao
    private val userFollowerDao: FollowerDao

    private val context: Context
    private val requestQueue: RequestQueue


    private val users: LiveData<List<User>>
    private lateinit var searchedUsers: MutableLiveData<List<User>>
//    private val userRepos: LiveData<List<Repo>>
//    private val userFollowers: LiveData<List<Follower>>
    private val searchUsers: List<User>


    init {
        context = application

        val userDb = UserDatabase.getInstance(application)
        userDao =  userDb.userDao()
        userDetailDao = userDb.userDetailDao()
        userRepoDao = userDb.userRepoDao()
        userFollowerDao = userDb.userFollowerDao()

        users = userDao.getAllUsers()
        requestQueue = MyVolley.getInstance().requestQueue

        searchUsers = ArrayList<User>()
        searchedUsers = MutableLiveData()

    }

    fun getAllUsers(): LiveData<List<User>> {
        return users
    }

    fun getUserDetails(id: Long): LiveData<UserDetail> {
        return userDetailDao.getUserDetail(id)
    }

    fun getUserRepos(id: Long): LiveData<List<Repo>> {
        return userRepoDao.getUserRepos(id)
    }

    fun getUserFollowers(login: String): LiveData<List<Follower>> {
        return userFollowerDao.getUserFollowers(login)
    }

    fun getSearchedUsers(): MutableLiveData<List<User>> {
        return searchedUsers
    }


    fun usersListApi() {
        val request = StringRequest(Request.Method.GET, "https://api.github.com/users",
                Response.Listener { response ->
                    val usersRemote = ArrayList<User>()

                    Log.d(TAG, "onResponse: users list response = $response")

                    try {
                        val responseArray = JSONArray(response)

                        for (i in 0 until responseArray.length()) {
                            val userObj = responseArray.getJSONObject(i)

                            val id = userObj.getLong("id")
                            val login = userObj.getString("login")
                            val avatarUrl = userObj.getString("avatar_url")

                            val user = User(id, login, avatarUrl)
                            usersRemote.add(user)
                        }

                        // Store the users retrieved from API to the database
                        Thread(Runnable { userDao.insertAllUsers(usersRemote) }).start()

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }, Response.ErrorListener { error ->
            try {
                Log.e(TAG, "onErrorResponse: error = " + error.message)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
        request.retryPolicy = DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

        requestQueue.add(request)

    }

    fun userDetailsApi(login: String) {
        val request = StringRequest(Request.Method.GET, "https://api.github.com/users/$login",
            Response.Listener { response ->
                val usersRemote = ArrayList<User>()

                Log.d(TAG, "onResponse: users details response = $response")

                try {
                    val responseObj = JSONObject(response)

                    val id = responseObj.getLong("id")
                    val loginName = responseObj.getString("login")
                    val avatarUrl = responseObj.getString("avatar_url")
                    val name = responseObj.getString("name")
                    val company = responseObj.getString("company")
                    val followers = responseObj.getInt("followers")
                    val following = responseObj.getInt("following")
                    val location = responseObj.getString("location")

                    val userDetails = UserDetail(id, loginName, avatarUrl, name, company, followers, following, location)


                    // Store the user detail retrieved from API to the database
                    Thread(Runnable { userDetailDao.insert(userDetails) }).start()

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { error ->
                try {
                    Log.e(TAG, "onErrorResponse: error = " + error.message)

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            })
        request.retryPolicy = DefaultRetryPolicy(
            30000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

        requestQueue.add(request)

    }

    fun userReposApi(login: String) {
        val request = StringRequest(Request.Method.GET, "https://api.github.com/users/$login/repos",
            Response.Listener { response ->
                val reposRemote = ArrayList<Repo>()

                Log.d(TAG, "onResponse: users repos response = $response")

                try {
                    val responseArray = JSONArray(response)

                    for (i in 0 until responseArray.length()) {
                        val repoObj = responseArray.getJSONObject(i)
                        val ownerObj = repoObj.getJSONObject("owner")

                        val id = repoObj.getLong("id")
                        val name = repoObj.getString("name")
                        val isPrivate = repoObj.getBoolean("private")
                        val description = repoObj.getString("description")
                        val url = repoObj.getString("url")
                        val userId = ownerObj.getLong("id")

                        val userRepo = Repo(id, name, isPrivate, description, url, userId)
                        reposRemote.add(userRepo)
                    }

                    // Store the celebs retrieved from API to the database
                    Thread(Runnable { userRepoDao.insertUserRepos(reposRemote) }).start()

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { error ->
                try {
                    Log.e(TAG, "onErrorResponse: error = " + error.message)

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            })
        request.retryPolicy = DefaultRetryPolicy(
            30000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

        requestQueue.add(request)

    }

    fun userFollowersApi(login: String) {
        val request = StringRequest(Request.Method.GET, "https://api.github.com/users/$login/followers",
            Response.Listener { response ->
                val followersRemote = ArrayList<Follower>()

                Log.d(TAG, "onResponse: users followers response = $response")

                try {
                    val responseArray = JSONArray(response)

                    for (i in 0 until responseArray.length()) {
                        val followerObj = responseArray.getJSONObject(i)

                        val id = followerObj.getLong("id")
                        val loginName = followerObj.getString("login")
                        val avatarUrl = followerObj.getString("avatar_url")


                        val follower = Follower(id, loginName, avatarUrl, login)
                        followersRemote.add(follower)
                    }

                    // Store the celebs retrieved from API to the database
                    Thread(Runnable { userFollowerDao.insertUserFollowers(followersRemote) }).start()

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { error ->
                try {
                    Log.e(TAG, "onErrorResponse: error = " + error.message)

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            })
        request.retryPolicy = DefaultRetryPolicy(
            30000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

        requestQueue.add(request)

    }

    fun searchUsersApi(query: String) {
        val request = StringRequest(Request.Method.GET, "https://api.github.com/search/users?q=$query",
            Response.Listener { response ->
                val usersRemote = ArrayList<User>()

                Log.d(TAG, "onResponse: search users response = $response")

                try {
                    val responseObj = JSONObject(response)
                    val itemArray = responseObj.getJSONArray("items")

                    for (i in 0 until itemArray.length()) {
                        val userObj = itemArray.getJSONObject(i)

                        val id = userObj.getLong("id")
                        val login = userObj.getString("login")
                        val avatarUrl = userObj.getString("avatar_url")

                        val user = User(id, login, avatarUrl)
                        usersRemote.add(user)
                    }

                    searchedUsers.value = usersRemote // Set the value for mutable live data to be the new list retrieved from the server

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { error ->
                try {
                    Log.e(TAG, "onErrorResponse: error = " + error.message)

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            })
        request.retryPolicy = DefaultRetryPolicy(
            30000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

        requestQueue.add(request)

    }

    companion object {
        private val TAG = "CelebRepository"
    }
}
