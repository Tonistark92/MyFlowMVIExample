package com.example.flowmviexample.util


import com.codingwithmitch.mviexample.util.FlowCallAdapter
import kotlinx.coroutines.flow.Flow
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class FlowCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Flow::class.java) {
            return null
        }

        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawObservableType = getRawType(observableType)

        if (rawObservableType != GenericApiResponse::class.java) {
            throw IllegalArgumentException("Type must be a parameterized type of GenericApiResponse<T>")
        }

        if (observableType !is ParameterizedType) {
            throw IllegalArgumentException("Resource must be parameterized")
        }

        val bodyType = getParameterUpperBound(0, observableType)
        return FlowCallAdapter<Any>(bodyType)
    }
}