package com.anshultiwari.illumnuschallenge.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.anshultiwari.illumnuschallenge.model.User
import com.anshultiwari.illumnuschallenge.repo.UserRepository

class UsersViewModel(application: Application): AndroidViewModel(application) {
    private val repository: UserRepository = UserRepository(application)

    fun fetchUsersAndStore() {
        repository.usersListApi()
    }

    fun getAllUsers(): LiveData<List<User>> {
        return repository.getAllUsers()
    }

}
