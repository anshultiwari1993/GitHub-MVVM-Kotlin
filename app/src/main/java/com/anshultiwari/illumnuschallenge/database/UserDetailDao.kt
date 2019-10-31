package com.anshultiwari.illumnuschallenge.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anshultiwari.illumnuschallenge.model.UserDetail

@Dao
interface UserDetailDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(userDetail: UserDetail)


    @Query("SELECT * FROM UserDetail WHERE id = :userId")
    fun getUserDetail(userId: Long): LiveData<UserDetail>
}