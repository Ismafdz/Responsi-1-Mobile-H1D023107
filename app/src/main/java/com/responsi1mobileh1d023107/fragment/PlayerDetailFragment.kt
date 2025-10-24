package com.responsi1mobileh1d023107.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.responsi1mobileh1d023107.databinding.FragmentPlayerDetailBinding
import com.responsi1mobileh1d023107.network.Player

class PlayerDetailFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentPlayerDetailBinding? = null
    private val binding get() = _binding!!

    private var player: Player? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        player = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(ARG_PLAYER, Player::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable(ARG_PLAYER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        player?.let {
            binding.tvDetailPlayerName.text = it.name ?: "Nama Tidak Diketahui"
            binding.tvDetailPlayerDob.text = it.dateOfBirth ?: "Tanggal Lahir Tidak Diketahui"
            binding.tvDetailPlayerNationality.text = it.nationality ?: "Kebangsaan Tidak Diketahui"
            binding.tvDetailPlayerPosition.text = it.position ?: "Posisi Tidak Diketahui"
        }
        binding.root.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "PlayerDetailFragment"
        private const val ARG_PLAYER = "arg_player"
        fun newInstance(player: Player): PlayerDetailFragment {
            val args = Bundle()
            args.putParcelable(ARG_PLAYER, player)
            val fragment = PlayerDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }
}