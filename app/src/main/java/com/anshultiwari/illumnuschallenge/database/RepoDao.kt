package com.anshultiwari.illumnuschallenge.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anshultiwari.illumnuschallenge.model.Repo

@Dao
interface RepoDao {
    @Query("SELECT * FROM Repo WHERE userId = :id")
    fun getUserRepos(id: Long): LiveData<List<Repo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserRepos(repos: List<Repo>)
}