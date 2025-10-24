package com.responsi1mobileh1d023107.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.responsi1mobileh1d023107.R
import com.responsi1mobileh1d023107.databinding.ItemPlayerCardBinding
import com.responsi1mobileh1d023107.network.Player

class SquadAdapter(
    private val context: Context,
    private val playerList: List<Player>,
    private val onPlayerClicked: (Player) -> Unit
) : RecyclerView.Adapter<SquadAdapter.PlayerViewHolder>() {

    class PlayerViewHolder(val binding: ItemPlayerCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val binding = ItemPlayerCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PlayerViewHolder(binding)
    }

    override fun getItemCount(): Int = playerList.size

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = playerList[position]

        val playerName = player.name ?: "Nama Tidak Diketahui"
        holder.binding.tvPlayerName.text = playerName
        holder.binding.tvPlayerNationality.text = player.nationality ?: "Kebangsaan Tidak Diketahui"


        val playerPosition = player.position ?: "Unknown"
        val defaultColor = ContextCompat.getColor(context, R.color.colorDefault)

        var cardColor = when {
            playerPosition.contains("Goalkeeper", ignoreCase = true) ||
                    playerPosition.equals("GK", ignoreCase = true) ->
                ContextCompat.getColor(context, R.color.colorGoalkeeper)

            playerPosition.contains("Defender", ignoreCase = true) ||
                    playerPosition.contains("Back", ignoreCase = true) ||
                    playerPosition.equals("LB", ignoreCase = true) ||
                    playerPosition.equals("RB", ignoreCase = true) ||
                    playerPosition.equals("CB", ignoreCase = true) ->
                ContextCompat.getColor(context, R.color.colorDefender)

            playerPosition.contains("Midfield", ignoreCase = true) ||
                    playerPosition.equals("DM", ignoreCase = true) ||
                    playerPosition.equals("CM", ignoreCase = true) ||
                    playerPosition.equals("AM", ignoreCase = true) ||
                    playerPosition.equals("LM", ignoreCase = true) ||
                    playerPosition.equals("RM", ignoreCase = true) ->
                ContextCompat.getColor(context, R.color.colorMidfielder)

            playerPosition.contains("Forward", ignoreCase = true) ||
                    playerPosition.contains("Attacker", ignoreCase = true) ||
                    playerPosition.contains("Winger", ignoreCase = true) ||
                    playerPosition.contains("Striker", ignoreCase = true) ||
                    playerPosition.equals("LW", ignoreCase = true) ||
                    playerPosition.equals("RW", ignoreCase = true) ||
                    playerPosition.equals("ST", ignoreCase = true) ||
                    playerPosition.equals("CF", ignoreCase = true) ->
                ContextCompat.getColor(context, R.color.colorForward)

            else ->
                defaultColor
        }
        if (cardColor == defaultColor) {
            when {
                playerName.contains("Kiko Femenía", ignoreCase = true) ||
                        playerName.contains("Ismael Bekhoucha", ignoreCase = true) ||
                        playerName.contains("Marc Vilaplana", ignoreCase = true) ||
                        playerName.contains("Davinchi", ignoreCase = true) -> {
                    cardColor = ContextCompat.getColor(context, R.color.colorDefender) // Set Biru
                }
            }
        }
        holder.binding.cardPlayer.setCardBackgroundColor(cardColor)

        val blackColor = ContextCompat.getColor(context, R.color.black)
        val whiteColor = ContextCompat.getColor(context, R.color.white)

        if (cardColor == defaultColor) {
            holder.binding.tvPlayerName.setTextColor(blackColor)
            holder.binding.tvPlayerNationality.setTextColor(blackColor)
        } else {
            holder.binding.tvPlayerName.setTextColor(whiteColor)
            holder.binding.tvPlayerNationality.setTextColor(whiteColor)
        }
        holder.itemView.setOnClickListener {
            onPlayerClicked(player)
        }
    }
}