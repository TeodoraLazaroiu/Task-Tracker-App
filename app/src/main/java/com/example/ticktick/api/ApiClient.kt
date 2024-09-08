package com.example.ticktick.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    fun makeRetrofitService(url: String): ApiInterface {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiInterface::class.java)
    }
}