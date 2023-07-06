package com.example.flowmviexample.api

import com.example.flowmviexample.model.MyDameResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("posts")
       fun getBlogPosts(): Flow<Response<MyDameResponse>>
//
//    @GET("placeholder/user/{userId}")
//    fun getUser(
//        @Path("userId") userId: String
//    ): Flow<GenericApiResponse<User>>
}