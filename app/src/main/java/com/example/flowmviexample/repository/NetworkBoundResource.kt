package com.example.flowmviexample.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.codingwithmitch.mviexample.util.Constants.Companion.TESTING_NETWORK_DELAY
import com.example.flowmviexample.util.ApiEmptyResponse
import com.example.flowmviexample.util.ApiErrorResponse
import com.example.flowmviexample.util.ApiSuccessResponse
import com.example.flowmviexample.util.DataState
import com.example.flowmviexample.util.GenericApiResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


abstract class NetworkBoundResource<ResponseObject, ViewStateType> {

    protected val result = MutableStateFlow<DataState<ViewStateType>>(DataState.loading(true))

    suspend fun handleNetworkCall(response: GenericApiResponse<ResponseObject>) {
        when (response) {
            is ApiSuccessResponse -> {
                handleApiSuccessResponse(response)
            }
            is ApiErrorResponse -> {
                println("DEBUG: NetworkBoundResource: ${response.errorMessage}")
                onReturnError(response.errorMessage)
            }
            is ApiEmptyResponse -> {
                println("DEBUG: NetworkBoundResource: Request returned NOTHING (HTTP 204)")
                onReturnError("HTTP 204. Returned NOTHING.")
            }
        }
    }

    fun onReturnError(message: String) {
        result.value = DataState.error(message)
    }

    abstract fun handleApiSuccessResponse(response: ApiSuccessResponse<ResponseObject>)

    abstract suspend fun createCall(): Flow<GenericApiResponse<ResponseObject>>

    fun asFlow(): Flow<DataState<ViewStateType>> = result
}