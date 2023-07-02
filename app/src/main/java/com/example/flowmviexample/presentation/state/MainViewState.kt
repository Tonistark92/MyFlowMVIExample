package com.example.flowmviexample.presentation.state

import com.codingwithmitch.mviexample.model.BlogPost
import com.codingwithmitch.mviexample.model.User

data class MainViewState(

    var blogPosts: List<BlogPost>? = null,

    var user: User? = null

)