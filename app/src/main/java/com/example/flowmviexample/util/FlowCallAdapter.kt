package com.codingwithmitch.mviexample.util


import com.example.flowmviexample.util.GenericApiResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class FlowCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, Flow<GenericApiResponse<R>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<R>): Flow<GenericApiResponse<R>> = callbackFlow {
        val started = AtomicBoolean(false)

        awaitClose { call.cancel() }

        if (started.compareAndSet(false, true)) {
            call.enqueue(object : Callback<R> {
                override fun onResponse(call: Call<R>, response: Response<R>) {
                    val genericApiResponse = GenericApiResponse.create(response)
                    try {
                        trySend(genericApiResponse).isSuccess
                    } catch (e: Exception) {
                        // Handle cancellation or other exceptions
                    }
                }

                override fun onFailure(call: Call<R>, throwable: Throwable) {
                    try {
                        trySend(GenericApiResponse.create(throwable)).isSuccess
                    } catch (e: Exception) {
                        // Handle cancellation or other exceptions
                    }
                }
            })
        }
    }
}