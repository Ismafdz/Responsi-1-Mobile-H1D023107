package com.responsi1mobileh1d023107.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("v4/teams/{id}")
    fun getTeamDetails(@Path("id") teamId: Int = 82): Call<TeamResponse>
}