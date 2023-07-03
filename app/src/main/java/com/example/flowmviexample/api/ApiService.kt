package com.example.flowmviexample.api

import com.codingwithmitch.mviexample.model.User
import com.example.flowmviexample.model.MyDameResponse
import com.example.flowmviexample.model.Post
import com.example.flowmviexample.util.GenericApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("posts")
       fun getBlogPosts(): Flow<Response<MyDameResponse>>
//
//    @GET("placeholder/user/{userId}")
//    fun getUser(
//        @Path("userId") userId: String
//    ): Flow<GenericApiResponse<User>>
}