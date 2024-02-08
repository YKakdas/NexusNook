package moadgara.main.games_detail

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import moadgara.base.extension.getAny
import moadgara.base.extension.observeOnce
import moadgara.base.extension.toPx
import moadgara.base.util.CoilUtil
import moadgara.base.viewBinding
import moadgara.data.games.entity.GameDetailResponse
import moadgara.main.R
import moadgara.main.databinding.FragmentGameDetailBinding
import moadgara.main.games_detail.listitems.GameDetailsAdapter
import moadgara.main.games_detail.listitems.GameDetailsHeaderData
import moadgara.uicomponent.AlertDialog
import moadgara.uicomponent.BaseFragment
import moadgara.uicomponent.LinearLayoutManagerWithAccurateOffset
import moadgara.uicomponent.custom_view.ProgressDialog
import moadgara.uicomponent.alertDialog
import moadgara.uicomponent.enforceSingleScrollDirection
import moadgara.uicomponent.overlay.Overlay
import moadgara.uicomponent.overlay.ToolbarFragment
import moadgara.uicomponent.overlay.ToolbarType
import org.koin.android.ext.android.inject

class GameDetailsFragment : BaseFragment(R.layout.fragment_game_detail) {

    private val viewModel: GameDetailsViewModel by inject()
    private val binding by viewBinding(FragmentGameDetailBinding::bind)
    private val progressDialog = ProgressDialog.newInstance()

    private lateinit var listAdapter: GameDetailsAdapter
    private lateinit var title: String
    private var gameId: Int? = null
    private var response: GameDetailResponse? = null
    private var overlay: Overlay? = null
    private var resumed = false

    companion object {
        const val KEY_GAME_ID = "game_id"
        const val KEY_GAME_NAME = "game_name"
        const val KEY_GAME_RESPONSE = "game_response"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameId = arguments?.getInt(KEY_GAME_ID)
        title = arguments?.getString(KEY_GAME_NAME).orEmpty()
        response = arguments?.getAny(KEY_GAME_RESPONSE)
        overlay = parentFragment as? Overlay
    }

    override fun onResume() {
        super.onResume()
        resumed = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        observeEvents()
    }

    private fun initialize() {
        setupRecyclerView()
        if (gameId != null) {
            viewModel.fetchData(gameId!!, response)
        }
    }

    private fun observeEvents() {
        observeData()
        if (!resumed) {
            observeError()
            observeProgress()
        }
    }

    private fun observeError() {
        viewModel.getMessage().observeOnce(this) {
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
            if (it != null) {
                listAdapter.submitList(it)
                val headerImage = it.filterIsInstance<GameDetailsHeaderData>().firstOrNull()?.defaultImage
                if (headerImage != null) {
                    CoilUtil.prefetchImages(requireContext(), listOf(headerImage)) {
                        progressDialog.showProgress(false, parentFragmentManager)
                    }
                } else {
                    progressDialog.showProgress(false, parentFragmentManager)
                }
            }
        }
    }

    private fun observeProgress() {
        viewModel.getProgress().observeOnce(viewLifecycleOwner) {
            if (it != null) {
                progressDialog.showProgress(it, parentFragmentManager)
            }
        }
    }

    private fun setupRecyclerView() {
        listAdapter = GameDetailsAdapter()

        binding.recyclerView.run {
            layoutManager = LinearLayoutManagerWithAccurateOffset(requireContext(), RecyclerView.VERTICAL, false)
            adapter = listAdapter
            setHasFixedSize(false)
            enforceSingleScrollDirection()
        }
        super.registerRecyclerViewScrollListener(binding.recyclerView, 180.toPx.toFloat(), 200.toPx.toFloat(), overlay)
    }


    override fun getTitle(): String? {
        return if (::title.isInitialized) {
            title
        } else {
            null
        }
    }

}