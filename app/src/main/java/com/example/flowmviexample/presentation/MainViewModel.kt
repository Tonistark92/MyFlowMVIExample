package com.example.flowmviexample.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingwithmitch.mviexample.util.AbsentLiveData
import com.example.flowmviexample.presentation.state.MainStateEvent
import com.example.flowmviexample.presentation.state.MainViewState
import com.example.flowmviexample.repository.Repository
import com.example.flowmviexample.util.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModel : ViewModel() {

    private val _stateEvent: MutableStateFlow<MainStateEvent> = MutableStateFlow(MainStateEvent.None)
    private val _viewState: MutableStateFlow<MainViewState> = MutableStateFlow(MainViewState())

    val viewState: MutableStateFlow<MainViewState>
        get() = _viewState


    val dataState: Flow<DataState<MainViewState>> = _stateEvent.flatMapLatest { stateEvent ->
        handleStateEvent(stateEvent)
    }
    init {
        viewModelScope.launch {


         Repository.getBlogPosts().collect{
             Log.d("TAG",it.data.toString())
         }
        }
    }
    fun handleStateEvent(stateEvent: MainStateEvent): Flow<DataState<MainViewState>>{
        println("DEBUG: New StateEvent detected: $stateEvent")
        when(stateEvent){

            is MainStateEvent.GetBlogPostsEvent -> {
                Log.d("TAG",Repository.getBlogPosts().toString())
                return Repository.getBlogPosts()
            }

            is MainStateEvent.GetUserEvent -> {
                return Repository.getUser(stateEvent.userId)
            }

            is MainStateEvent.None ->{
                return flow { }
            }
        }
    }
}