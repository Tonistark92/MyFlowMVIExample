package com.example.flowmviexample.repository

import androidx.lifecycle.LiveData
import com.codingwithmitch.mviexample.model.BlogPost
import com.codingwithmitch.mviexample.model.User
import com.codingwithmitch.mviexample.util.*
import com.codingwithmitch.mviexample.util.Constants.Companion.TESTING_NETWORK_DELAY
import com.example.flowmviexample.api.MyRetrofitBuilder
import com.example.flowmviexample.presentation.state.MainViewState
import com.example.flowmviexample.util.ApiSuccessResponse
import com.example.flowmviexample.util.DataState
import com.example.flowmviexample.util.GenericApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

object Repository {

    fun getBlogPosts(): Flow<DataState<MainViewState>> {
        return object : NetworkBoundResource <List<BlogPost>, MainViewState>(){
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<BlogPost>>) {
                result.value = DataState.data(
                    null,
                    MainViewState(
                        blogPosts = response.body,
                        user = null
                    )
                )
            }

            override suspend fun createCall(): Flow<GenericApiResponse<List<BlogPost>>> {
                return MyRetrofitBuilder.apiService.getBlogPosts()
            }

        }.asFlow()
    }

    fun getUser(userId: String): Flow<DataState<MainViewState>> {
        return object: NetworkBoundResource<User, MainViewState>(){

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<User>) {
                result.value = DataState.data(
                    null,
                    MainViewState(
                        blogPosts = null,
                        user = response.body
                    )
                )
            }

            override suspend fun createCall(): Flow<GenericApiResponse<User>> {
                return MyRetrofitBuilder.apiService.getUser(userId)
            }

        }.asFlow()
    }
}




























