package com.responsi1mobileh1d023107

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.responsi1mobileh1d023107.databinding.ActivityMainBinding
import com.responsi1mobileh1d023107.network.TeamResponse
import com.responsi1mobileh1d023107.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private var teamDataCache: TeamResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()
        setupClickListeners()
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(this) { message ->
            if (message != null) {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }

        viewModel.teamData.observe(this) { teamData ->
            teamDataCache = teamData // Simpan data
            populateUI(teamData)
        }
    }

    private fun populateUI(team: TeamResponse) {
        binding.tvClubName.text = team.name
        binding.tvClubDetails.text = "Didirikan: ${team.founded} | Stadion: ${team.venue}"

        Glide.with(this)
            .load(team.crestUrl)
            .into(binding.imgClubCrest)

        binding.tvShortProfile.text = """
            Getafe CF adalah klub sepak bola profesional yang bermarkas di kota kecil Getafe, dekat Madrid. Dikenal dengan gaya permainan yang disiplin dan kolektif, klub ini berhasil menembus La Liga pada tahun 2004 dan sejak itu menjadi salah satu tim yang stabil di kompetisi tertinggi Spanyol. Dengan sejarah perjuangan yang panjang dan dukungan fanatik dari pendukungnya, Los Azulones telah membuktikan diri sebagai simbol kerja keras dan keteguhan di dunia sepak bola Spanyol.
        """.trimIndent()
    }

    private fun setupClickListeners() {
        binding.cardClubHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }

        binding.cardHeadCoach.setOnClickListener {
            teamDataCache?.coach?.let { coach ->
                val intent = Intent(this, CoachActivity::class.java)
                intent.putExtra(CoachActivity.EXTRA_COACH, coach)
                startActivity(intent)
            } ?: Toast.makeText(this, "Data pelatih belum dimuat", Toast.LENGTH_SHORT).show()
        }

        binding.cardTeamSquad.setOnClickListener {
            teamDataCache?.squad?.let { squad ->
                if (squad.isNotEmpty()) {
                    val intent = Intent(this, SquadActivity::class.java)
                    intent.putParcelableArrayListExtra(SquadActivity.EXTRA_SQUAD, ArrayList(squad))
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Data skuad kosong", Toast.LENGTH_SHORT).show()
                }
            } ?: Toast.makeText(this, "Data skuad belum dimuat", Toast.LENGTH_SHORT).show()
        }
    }
}