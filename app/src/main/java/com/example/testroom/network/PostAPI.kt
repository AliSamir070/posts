package com.example.testroom.network

import com.example.testroom.Post
import retrofit2.http.GET

interface PostAPI {
    @GET("posts")
    suspend fun getAllPosts():List<Post>
}