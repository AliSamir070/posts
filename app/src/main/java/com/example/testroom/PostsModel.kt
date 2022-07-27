package com.example.testroom

import android.app.Application
import androidx.lifecycle.*
import com.example.testroom.util.NetworkState
import com.example.testroom.util.PostsState
import kotlinx.coroutines.launch

class PostsModel(private val repository: Repository):ViewModel() {
    private val _postsList = MutableLiveData<List<Post>>()
    val postsList:LiveData<List<Post>>
    get() = _postsList
    val state:LiveData<PostsState> = repository.state

    /*fun AddOnePost(post: Post){
        viewModelScope.launch {
            repository.AddPost(post)
        }
    }*/

    fun GetPosts(state: NetworkState){
        viewModelScope.launch {
            if(state == NetworkState.Connected){
                _postsList.value = repository.GetAllPosts()
            }else{
                _postsList.value = repository.GetCachePosts()
            }
        }
    }
}

class PostsModelFactory(private val repository: Repository):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostsModel::class.java)){
            @Suppress("Unchecked_Cast")
            return PostsModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
