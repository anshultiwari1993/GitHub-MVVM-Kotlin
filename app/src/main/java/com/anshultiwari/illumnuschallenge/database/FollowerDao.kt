package com.anshultiwari.illumnuschallenge.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anshultiwari.illumnuschallenge.model.Follower

@Dao
interface FollowerDao {
    @Query("SELECT * FROM Follower WHERE userLogin = :login")
    fun getUserFollowers(login: String): LiveData<List<Follower>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserFollowers(repos: List<Follower>)
}