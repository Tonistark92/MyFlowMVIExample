package com.example.flowmviexample.repository

import android.util.Log
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException


abstract class NetworkBoundResource<ResponseObject, ViewStateType> {

    protected val result = MutableStateFlow<DataState<ViewStateType>>(DataState.loading(true))
    init {


        GlobalScope.launch(IO){
            try {
                val apiResponse = createCall()

                apiResponse.collect{
                    Log.d("TAG",it.errorBody().toString())
                    Log.d("TAG",it.message())

                    handleNetworkCall(it)
                }
            }
            catch (e: IOException) {
                Log.d("TAG", "No Internet")
                result.value = DataState.error("No Internet")
            }  catch (e: ConnectException) {
                Log.d("TAG", "ConnectException: ${e.message}")
                result.value = DataState.error("Connection error occurred")
            } catch (e: HttpException) {
                Log.d("TAG", "HttpException: ${e.message}")
                result.value = DataState.error("API error occurred")
            } catch (e: Exception) {
                Log.d("TAG", "Unknown error: ${e.message}")
                result.value = DataState.error("Unknown error occurred")
            }


        }
    }
    suspend fun handleNetworkCall(response: Response<ResponseObject>) {
        when (response.code()) {
            200 ->  handleApiSuccessResponse(response)
            400 -> onReturnError(response.message())
            404 -> onReturnError("API endpoint not found: ${response.message()}")
            500 -> onReturnError("HTTP 204. Returned NOTHING.")
        }
    }

    fun onReturnError(message: String) {
        result.value = DataState.error(message)
    }

    abstract fun handleApiSuccessResponse(response: Response<ResponseObject>)

    abstract suspend fun createCall(): Flow<Response<ResponseObject>>

    fun asFlow(): Flow<DataState<ViewStateType>> = result
}