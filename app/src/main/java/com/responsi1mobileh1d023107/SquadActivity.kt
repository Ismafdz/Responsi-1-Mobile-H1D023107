package com.responsi1mobileh1d023107

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.responsi1mobileh1d023107.adapter.SquadAdapter
import com.responsi1mobileh1d023107.databinding.ActivitySquadBinding
import com.responsi1mobileh1d023107.fragment.PlayerDetailFragment
import com.responsi1mobileh1d023107.network.Player

class SquadActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySquadBinding
    private var dismissListener: (() -> Unit)? = null

    companion object {
        const val EXTRA_SQUAD = "extra_squad"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySquadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val squadList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableArrayListExtra(EXTRA_SQUAD, Player::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableArrayListExtra(EXTRA_SQUAD)
        }

        if (squadList != null) {
            squadList.shuffle()
            setupRecyclerView(squadList)
        }
    }

    private fun setupRecyclerView(squadList: ArrayList<Player>) {
        val adapter = SquadAdapter(this, squadList) { player ->
            showPlayerDetails(player)
        }
        binding.rvSquad.layoutManager = LinearLayoutManager(this)
        binding.rvSquad.adapter = adapter
    }
    private fun showPlayerDetails(player: Player) {
        val oldFragment = supportFragmentManager.findFragmentByTag(PlayerDetailFragment.TAG)

        if (oldFragment != null && oldFragment is DialogFragment) {
            dismissListener = {
                PlayerDetailFragment.newInstance(player).show(supportFragmentManager, PlayerDetailFragment.TAG)
                (oldFragment as? DialogFragment)?.dialog?.setOnDismissListener(null)
                dismissListener = null
            }

            oldFragment.dialog?.setOnDismissListener { dismissListener?.invoke() }
            oldFragment.dismiss()
        } else {
            PlayerDetailFragment.newInstance(player).show(supportFragmentManager, PlayerDetailFragment.TAG)
        }
    }
}