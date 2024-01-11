package moadgara.main.games_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import moadgara.base.viewBinding
import moadgara.main.R
import moadgara.main.databinding.FragmentGameDetailBinding
import moadgara.uicomponent.AlertDialog
import moadgara.uicomponent.alertDialog
import org.koin.android.ext.android.inject

class GameDetailsFragment : Fragment(R.layout.fragment_game_detail) {

    private val viewModel: GameDetailsViewModel by inject()
    private val binding by viewBinding(FragmentGameDetailBinding::bind)

    private var gameId: Int? = null

    companion object {
        const val KEY_GAME_ID = "game_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameId = arguments?.getInt(KEY_GAME_ID)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        observeEvents()
    }

    private fun initialize() {
        if (gameId != null) {
            viewModel.fetchData(gameId!!)
        }
    }

    private fun observeEvents() {
        observeError()
        observeData()
    }

    private fun observeError() {
        viewModel.getMessage().observe(viewLifecycleOwner) {
            alertDialog(requireContext()) {
                title(R.string.generic_error_title)
                description(it ?: resources.getString(R.string.generic_error_description))
                neutralText(R.string.alert_dialog_neutral_button_text)
                type(AlertDialog.Type.ERROR)
            }
        }
    }

    private fun observeData() {
        viewModel.getGameDetailsData().observe(viewLifecycleOwner) {
            it.let { binding.data = it }
        }
    }
}