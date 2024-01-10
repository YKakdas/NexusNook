package moadgara.main.games_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import moadgara.main.R
import moadgara.main.databinding.FragmentGameDetailBinding

class GameDetailFragment : Fragment(R.layout.fragment_game_detail) {

    private lateinit var binding: FragmentGameDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

}