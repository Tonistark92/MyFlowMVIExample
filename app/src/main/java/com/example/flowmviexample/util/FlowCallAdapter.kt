package com.codingwithmitch.mviexample.util


import com.example.flowmviexample.util.ApiEmptyResponse
import com.example.flowmviexample.util.GenericApiResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean


class ResponseFlowCallAdapter<T>(
    private val responseType: Type
) : CallAdapter<T, Flow<Response<T>>> {

    override fun responseType(): Type = responseType

    override fun adapt(call: Call<T>): Flow<Response<T>> = flow {
        val response = call.execute()

        if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                emit(Response.success(responseBody, response.raw()))
            } else {
                emit(Response.error<T>(response.code(), response.errorBody()))
            }
        } else {
            emit(Response.error<T>(response.code(), response.errorBody()))
        }
    }
}