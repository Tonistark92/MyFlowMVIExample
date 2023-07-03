package com.example.flowmviexample.repository

import android.util.Log
import com.codingwithmitch.mviexample.model.BlogPost
import com.codingwithmitch.mviexample.model.User
import com.example.flowmviexample.api.MyRetrofitBuilder
import com.example.flowmviexample.model.MyDameResponse
import com.example.flowmviexample.model.Post
import com.example.flowmviexample.presentation.state.MainViewState
import com.example.flowmviexample.util.ApiSuccessResponse
import com.example.flowmviexample.util.DataState
import com.example.flowmviexample.util.GenericApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

object Repository {

    fun getBlogPosts(): Flow<DataState<MainViewState>> {
        return object : NetworkBoundResource <MyDameResponse, MainViewState>(){
            override fun handleApiSuccessResponse(response: Response<MyDameResponse>) {
                Log.d("TAG","in repo"+response.body().toString())
                result.value = DataState.data(
                    null,
                    MainViewState(
                        blogPosts = response.body(),
                        user = null
                    )
                )
            }

            override suspend fun createCall(): Flow<Response<MyDameResponse>> {
                MyRetrofitBuilder.apiService.getBlogPosts().collect{
                    Log.d("TAG","in repo CALL"+it.body().toString())
                }
                return MyRetrofitBuilder.apiService.getBlogPosts()
            }

        }.asFlow()
    }

//    fun getUser(userId: String): Flow<DataState<MainViewState>> {
//        return object: NetworkBoundResource<User, MainViewState>(){
//
//            override fun handleApiSuccessResponse(response: ApiSuccessResponse<User>) {
//                result.value = DataState.data(
//                    null,
//                    MainViewState(
//                        blogPosts = null,
//                        user = response.body
//                    )
//                )
//            }
//
//            override suspend fun createCall(): Flow<GenericApiResponse<User>> {
//                return MyRetrofitBuilder.apiService.getUser(userId)
//            }
//
//        }.asFlow()
//    }
}




























