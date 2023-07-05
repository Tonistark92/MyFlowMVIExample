package com.example.flowmviexample.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingwithmitch.mviexample.util.AbsentLiveData
import com.example.flowmviexample.api.MyRetrofitBuilder
import com.example.flowmviexample.presentation.state.MainStateEvent
import com.example.flowmviexample.presentation.state.MainViewState
import com.example.flowmviexample.repository.Repository
import com.example.flowmviexample.util.ApiEmptyResponse
import com.example.flowmviexample.util.ApiErrorResponse
import com.example.flowmviexample.util.ApiSuccessResponse
import com.example.flowmviexample.util.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModel : ViewModel() {

    private val _stateEvent: MutableStateFlow<MainStateEvent> =
        MutableStateFlow(MainStateEvent.None)
    private val _viewState: MutableStateFlow<MainViewState> = MutableStateFlow(MainViewState())

    val viewState: StateFlow<MainViewState>
        get() = _viewState

    private val _dataState: MutableStateFlow<DataState<MainViewState>> =
        MutableStateFlow(DataState())
    val dataState: StateFlow<DataState<MainViewState>> = _dataState
//    val dataState: Flow<DataState<MainViewState>> = _stateEvent.flatMapLatest { stateEvent ->
//        handleStateEvent(stateEvent)
//    }
    init {
        viewModelScope.launch {
            _stateEvent.collectLatest { stateEvent ->
                handleStateEvent(stateEvent)
            }
        }
    }

    fun setStateEvent(event: MainStateEvent) {
        _stateEvent.value = event
    }

    private suspend fun handleStateEvent(stateEvent: MainStateEvent) {
        when (stateEvent) {
            is MainStateEvent.GetBlogPostsEvent -> {
                _dataState.value = DataState.loading(isLoading = true)
                try {
                    delay(1000L)
                    val response = Repository.getBlogPosts()
                    response.collect { dataState ->
                        _viewState.value = _viewState.value.copy(blogPosts = dataState.data?.blogPosts)
                        _dataState.value = DataState.data(data = _viewState.value)
                    }
                    _dataState.value = DataState.loading(isLoading = false)

                } catch (e: Exception) {
                    _dataState.value = DataState.error(message = "Error fetching blog posts")
                }
            }

            is MainStateEvent.GetUserEvent -> {}
            is MainStateEvent.None -> {}
        }
    }
}


