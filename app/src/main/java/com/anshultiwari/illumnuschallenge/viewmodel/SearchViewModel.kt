package com.anshultiwari.illumnuschallenge.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anshultiwari.illumnuschallenge.model.User
import com.anshultiwari.illumnuschallenge.repo.UserRepository

class SearchViewModel(application: Application): AndroidViewModel(application) {
    private val repository: UserRepository = UserRepository(application)

    fun fetchSearchedUsers(query: String) {
        repository.searchUsersApi(query)
    }

    fun getSearchedUsers(): MutableLiveData<List<User>> {
        return repository.getSearchedUsers()
    }

}
