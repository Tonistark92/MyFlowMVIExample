package com.example.flowmviexample.util


import com.codingwithmitch.mviexample.util.ResponseFlowCallAdapter
import com.example.flowmviexample.model.MyDameResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.CallAdapter
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class FlowCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (Flow::class.java != getRawType(returnType)) {
            return null
        }
        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawObservableType = getRawType(observableType)
        if (rawObservableType != MyDameResponse::class.java) {
            throw IllegalArgumentException("Flow must be parameterized with MyDameResponse")
        }
        if (observableType !is ParameterizedType) {
            throw IllegalArgumentException("Flow must be parameterized with MyDameResponse")
        }
        return ResponseFlowCallAdapter<Any>(observableType)
    }
}