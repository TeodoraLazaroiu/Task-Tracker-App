package com.example.ticktick.api

import com.example.ticktick.api.dto.Quote
import com.example.ticktick.api.dto.Task
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("api/v1/todos")
    suspend fun getTasks(): Response<List<Task>>
    @GET("api/random")
    suspend fun getQuote(): Response<List<Quote>>
}