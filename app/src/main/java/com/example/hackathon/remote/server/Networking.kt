package com.example.hackathon.remote.server

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Networking {
    private const val BASE_URL =  "https://stayzee.herokuapp.com/"
    private fun createRetrofitInstance(): NetworkService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .build()
            )
            .build()
            .create(NetworkService::class.java)
    }

    fun server() = createRetrofitInstance()
}