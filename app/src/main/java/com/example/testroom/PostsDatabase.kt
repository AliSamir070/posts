package com.example.testroom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [Post::class], version = 1)
abstract class PostsDatabase: RoomDatabase() {

    abstract val dao:PostsDatabaseDao
    companion object{
        @Volatile
        var INSTANCE:PostsDatabase? = null
        fun getDatabase(context:Context): PostsDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(context , PostsDatabase::class.java,"PostsDatabase")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}