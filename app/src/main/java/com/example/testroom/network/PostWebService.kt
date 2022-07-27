package com.example.testroom.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val POST_BASE_URL = "https://jsonplaceholder.typicode.com/"
val postMoshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
val postRetrofit = Retrofit.Builder()
    .baseUrl(POST_BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(postMoshi))
    .build()

object PostWebService{
    val postAPIService:PostAPI by lazy {
        postRetrofit.create(PostAPI::class.java)
    }
}