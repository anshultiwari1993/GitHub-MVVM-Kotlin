package com.anshultiwari.illumnuschallenge.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDetail(@PrimaryKey val id: Long,
                      val login: String,
                      @ColumnInfo(name = "avatar_url") val avatarUrl: String,
                      val name: String,
                      val company: String,
                      val followers: Int,
                      val following: Int,
                      val location: String
)