package com.example.flowmviexample.presentation.state

sealed class MainStateEvent {

    object GetBlogPostsEvent : MainStateEvent()

    class GetUserEvent(
        val userId: String
    ): MainStateEvent()

    object None : MainStateEvent()


}