package com.responsi1mobileh1d023107

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.responsi1mobileh1d023107.databinding.ActivityCoachBinding
import com.responsi1mobileh1d023107.network.Coach

class CoachActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoachBinding

    companion object {
        const val EXTRA_COACH = "extra_coach"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoachBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val coach = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_COACH, Coach::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_COACH)
        }

        if (coach != null) {
            populateCoachData(coach)
        }
    }

    private fun populateCoachData(coach: Coach) {
        binding.tvCoachName.text = coach.name
        binding.tvCoachNationality.text = "Kebangsaan: ${coach.nationality}"
        binding.tvCoachDob.text = "Lahir: ${coach.dateOfBirth}"
    }
}