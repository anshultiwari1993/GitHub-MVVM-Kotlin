package com.anshultiwari.illumnuschallenge.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anshultiwari.illumnuschallenge.model.Follower
import com.anshultiwari.illumnuschallenge.model.Repo
import com.anshultiwari.illumnuschallenge.model.User
import com.anshultiwari.illumnuschallenge.model.UserDetail

@Database(entities = [User::class, UserDetail::class, Repo::class, Follower::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun userDetailDao(): UserDetailDao
    abstract fun userRepoDao(): RepoDao
    abstract fun userFollowerDao(): FollowerDao

    companion object {

        private var instance: UserDatabase? = null

        @Synchronized
        fun getInstance(context: Context): UserDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext, UserDatabase::class.java, "user_database")
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return instance as UserDatabase
        }
    }
}
