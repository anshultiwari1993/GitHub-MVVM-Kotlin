package com.anshultiwari.illumnuschallenge.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anshultiwari.illumnuschallenge.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun getAllUsers(): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllUsers(users: List<User>)
}