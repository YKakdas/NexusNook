package moadgara.main.games_detail

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import moadgara.base.BaseFragment
import moadgara.base.extension.observeOnce
import moadgara.base.extension.px
import moadgara.base.viewBinding
import moadgara.main.R
import moadgara.main.databinding.FragmentGameDetailBinding
import moadgara.main.games_detail.listitems.GameDetailsAdapter
import moadgara.uicomponent.AlertDialog
import moadgara.uicomponent.ProgressDialog
import moadgara.uicomponent.alertDialog
import moadgara.uicomponent.overlay.ToolbarFragment
import org.koin.android.ext.android.inject

class GameDetailsFragment : BaseFragment(R.layout.fragment_game_detail), ToolbarFragment {

    private val viewModel: GameDetailsViewModel by inject()
    private val binding by viewBinding(FragmentGameDetailBinding::bind)
    private val progressDialog = ProgressDialog.newInstance()

    private lateinit var listAdapter: GameDetailsAdapter
    private lateinit var title: String
    private var gameId: Int? = null


    companion object {
        const val KEY_GAME_ID = "game_id"
        const val KEY_GAME_NAME = "game_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameId = arguments?.getInt(KEY_GAME_ID)
        title = arguments?.getString(KEY_GAME_NAME).orEmpty()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        observeEvents()
    }

    private fun initialize() {
        setupRecyclerView()
        if (gameId != null) {
            progressDialog.showProgress(true, parentFragmentManager)
            viewModel.fetchData(gameId!!)
        }
    }

    private fun observeEvents() {
        observeError()
        observeData()
    }

    private fun observeError() {
        viewModel.getMessage().observeOnce(viewLifecycleOwner) {
            progressDialog.showProgress(false, parentFragmentManager)
            alertDialog(requireContext()) {
                title(R.string.generic_error_title)
                description(it ?: resources.getString(R.string.generic_error_description))
                neutralText(R.string.alert_dialog_neutral_button_text)
                type(AlertDialog.Type.ERROR)
            }
        }
    }

    private fun observeData() {
        viewModel.getGameDetailsData().observeOnce(viewLifecycleOwner) {
            listAdapter.submitList(it)
            progressDialog.showProgress(false, parentFragmentManager)
        }
    }

    private fun setupRecyclerView() {
        listAdapter = GameDetailsAdapter()

        binding.recyclerView.run {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = listAdapter
            setHasFixedSize(false)
        }
        super.registerRecyclerViewScrollListener(binding.recyclerView, 200.px.toFloat(), 240.px.toFloat())
    }


    override fun getTitle(): String {
        return if (::title.isInitialized) {
            title
        } else {
            ""
        }
    }

    override fun showToolbar(): Boolean = false

    override fun onBackPressed(): Boolean = false
}