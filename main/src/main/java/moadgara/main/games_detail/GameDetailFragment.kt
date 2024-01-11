package moadgara.main.games_detail

import androidx.fragment.app.Fragment
import moadgara.base.viewBinding
import moadgara.main.R
import moadgara.main.databinding.FragmentGameDetailBinding

class GameDetailFragment : Fragment(R.layout.fragment_game_detail) {

    private val binding by viewBinding(FragmentGameDetailBinding::bind)

    companion object {
        const val GAME_ID = "game_id"
    }

}