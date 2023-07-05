package com.example.flowmviexample.presentation.state

import com.codingwithmitch.mviexample.model.BlogPost
import com.codingwithmitch.mviexample.model.User
import com.example.flowmviexample.model.MyDameResponse
import com.example.flowmviexample.model.Post

data class MainViewState(

    var blogPosts: MyDameResponse? = null,
    var user: User? = null

)