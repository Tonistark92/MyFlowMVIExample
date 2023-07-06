package com.example.flowmviexample.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flowmviexample.presentation.state.MainStateEvent
import com.example.flowmviexample.presentation.theme.FlowMVIExampleTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = viewModel()

            FlowMVIExampleTheme {
                val dataState by viewModel.dataState.collectAsState()
                val viewState by viewModel.viewState.collectAsState()

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    if (dataState.loading) {
                        CircularProgressIndicator()
                    }
                    Column(
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,

                        ) {
                        Button(onClick = { viewModel.setStateEvent(MainStateEvent.GetBlogPostsEvent) }) {
                            Text(text = "Get Posts Now !")
                        }
                        if (!viewState.blogPosts.isNullOrEmpty()) {
                            LazyColumn {
                                items(viewState.blogPosts!!) { blogPost ->
                                    Surface(
                                        shape = RoundedCornerShape(20.dp),
                                        color = Color.DarkGray,
                                        modifier = Modifier.padding(10.dp)
                                    ) {
                                        Text(
                                            text = blogPost.title,
                                            color = Color.White,
                                            modifier = Modifier.padding(10.dp)
                                        )

                                    }
                                }
                            }
                        } else if (dataState.message.isNullOrEmpty()) {
                            Text(
                                text = "click the button 🥺",
                                modifier = Modifier.padding(10.dp)
                            )
                        } else {
                            Text(
                                text = dataState.message.toString(),
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
