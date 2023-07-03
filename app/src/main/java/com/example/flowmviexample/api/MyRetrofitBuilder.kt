package com.example.flowmviexample.api

import com.example.flowmviexample.model.MyDameResponse
import com.example.flowmviexample.model.Post
import com.example.flowmviexample.util.ApiEmptyResponse
import com.example.flowmviexample.util.ApiSuccessResponse
import com.example.flowmviexample.util.GenericApiResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.InstanceCreator
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.Response
import tech.thdev.network.flowcalladapterfactory.FlowCallAdapterFactory
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

object MyRetrofitBuilder {

    const val BASE_URL: String = "https://jsonplaceholder.typicode.com/"

    val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(FlowCallAdapterFactory())
//            .addCallAdapterFactory(ResultCallAdapterFactory.create())
    }


    val apiService: ApiService by lazy {
        retrofitBuilder
            .build()
            .create(ApiService::class.java)
    }
}