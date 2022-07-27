package com.example.testroom

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.testroom.network.PostWebService
import com.example.testroom.util.PostsState
import java.lang.Exception

class Repository(val postsDatabaseDao: PostsDatabaseDao) {
    val state = MutableLiveData<PostsState>()
    suspend fun GetAllPosts():List<Post>{
        state.value = PostsState.Loading
        Log.d("TAG", "GetAllPosts: ")
        var posts:List<Post>? = null
        try {
            posts = PostWebService.postAPIService.getAllPosts()
            state.value = PostsState.Done
        }catch (e:Exception){
            state.value = PostsState.Error
        }
        postsDatabaseDao.deleteAll()
        postsDatabaseDao.addAllPosts(posts!!)
        return posts
    }
    suspend fun AddPost(post: Post){
        postsDatabaseDao.insertPost(post)
    }

    suspend fun GetCachePosts():List<Post>{
        Log.d("TAG", "GetCachePosts: ")
        return postsDatabaseDao.getAllPosts()
    }
}