package com.responsi1mobileh1d023107

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.responsi1mobileh1d023107.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}