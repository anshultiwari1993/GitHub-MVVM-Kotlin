package com.anshultiwari.illumnuschallenge.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.anshultiwari.illumnuschallenge.model.Repo
import com.anshultiwari.illumnuschallenge.model.UserDetail
import com.anshultiwari.illumnuschallenge.repo.UserRepository

class UserDetailsViewModel(application: Application): AndroidViewModel(application) {
    private val repository = UserRepository(application)

    fun fetchUserDetailsAndStore(login: String) {
        repository.userDetailsApi(login)
    }

    fun fetchUserReposAndStore(login: String) {
        repository.userReposApi(login)
    }

    fun getUserDetails(id: Long): LiveData<UserDetail> {
        return repository.getUserDetails(id)
    }

    fun getUserRepos(id: Long): LiveData<List<Repo>> {
        return repository.getUserRepos(id)
    }


}
