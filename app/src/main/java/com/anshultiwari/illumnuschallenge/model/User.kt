package com.anshultiwari.illumnuschallenge.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(@PrimaryKey val id: Long,
                val login: String,
                @ColumnInfo(name = "avatar_url") val avatarUrl: String
)