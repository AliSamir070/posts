package com.example.testroom

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "Post")
data class Post(
    @Json(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    @NonNull
    @ColumnInfo(name = "userid")
    @Json(name = "userId")
    val userId:Int,

    @NonNull
    @ColumnInfo(name = "title")
    @Json(name = "title")
    val title:String,

    @NonNull
    @ColumnInfo(name = "body")
    @Json(name = "body")
    val body:String
)
