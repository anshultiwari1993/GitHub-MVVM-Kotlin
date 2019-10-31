package com.anshultiwari.illumnuschallenge.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.anshultiwari.illumnuschallenge.model.Follower
import com.anshultiwari.illumnuschallenge.repo.UserRepository

class FollowersViewModel(application: Application): AndroidViewModel(application) {
    private val repository: UserRepository = UserRepository(application)

    fun fetchFollowersAndStore(login: String) {
        repository.userFollowersApi(login)
    }

    fun getUserFollowers(login: String): LiveData<List<Follower>> {
        return repository.getUserFollowers(login)
    }

}
