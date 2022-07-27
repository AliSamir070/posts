package com.example.testroom

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PostsDatabaseDao {
   @Query("select * from Post where userid = :userId")
   fun getPostsByUserId(userId:Int):List<Post>

   @Query("select * from Post")
   suspend fun  getAllPosts():List<Post>

   @Insert
   fun insertPost(post: Post)

   @Query("Delete from Post")
   suspend fun deleteAll()

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun addAllPosts(posts:List<Post>)

}