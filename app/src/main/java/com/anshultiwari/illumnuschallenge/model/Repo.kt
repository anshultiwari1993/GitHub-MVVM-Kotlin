package com.anshultiwari.illumnuschallenge.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Repo(@PrimaryKey val id: Long,
                val name: String,
                @ColumnInfo(name = "private") val isPrivate: Boolean,
                val description: String,
                val url: String,
                val userId: Long
)