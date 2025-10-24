package com.responsi1mobileh1d023107.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val API_TOKEN = "edd01e6736fb4de7b091864c38ac930f"
    private const val BASE_URL = "https://api.football-data.org/"

    val apiService: ApiService by lazy {

        val authInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("X-Auth-Token", API_TOKEN)
                .build()
            chain.proceed(request)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}