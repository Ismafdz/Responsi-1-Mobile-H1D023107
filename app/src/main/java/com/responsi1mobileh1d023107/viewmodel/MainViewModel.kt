package com.responsi1mobileh1d023107.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.responsi1mobileh1d023107.network.ApiClient
import com.responsi1mobileh1d023107.network.TeamResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _teamData = MutableLiveData<TeamResponse>()
    val teamData: LiveData<TeamResponse> = _teamData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    init {
        fetchTeamData()
    }

    private fun fetchTeamData() {
        _isLoading.value = true
        ApiClient.apiService.getTeamDetails().enqueue(object : Callback<TeamResponse> {

            override fun onResponse(call: Call<TeamResponse>, response: Response<TeamResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _teamData.value = response.body()
                } else {
                    _errorMessage.value = "Gagal memuat data: ${response.message()}"
                    Log.e("MainViewModel", "Error: ${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<TeamResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "Gagal terhubung: ${t.message}"
                Log.e("MainViewModel", "Failure: ${t.message}", t)
            }
        })
    }
}