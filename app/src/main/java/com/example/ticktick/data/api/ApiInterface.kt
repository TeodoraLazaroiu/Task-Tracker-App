package com.example.ticktick.data.api

import com.example.ticktick.model.QuoteAPIResponse
import com.example.ticktick.model.TaskAPIResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("api/v1/todos")
    suspend fun getTasks(): Response<List<TaskAPIResponse>>
    @GET("api/random")
    suspend fun getQuote(): Response<List<QuoteAPIResponse>>
}