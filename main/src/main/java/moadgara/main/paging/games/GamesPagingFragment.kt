package moadgara.main.paging.games

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import moadgara.base.extension.getAny
import moadgara.base.util.ResourceProvider
import moadgara.base.viewBinding
import moadgara.domain.ListType
import moadgara.main.R
import moadgara.main.databinding.LayoutGamesPagingFragmentBinding
import moadgara.uicomponent.AlertDialog
import moadgara.uicomponent.BaseFragment
import moadgara.uicomponent.CustomLinearSnapHelper
import moadgara.uicomponent.PreloadLinearLayoutManager
import moadgara.uicomponent.custom_view.ProgressDialog
import moadgara.uicomponent.alertDialog
import moadgara.uicomponent.overlay.ToolbarFragment
import moadgara.uicomponent.overlay.ToolbarType
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class GamesPagingFragment : BaseFragment(R.layout.layout_games_paging_fragment), ToolbarFragment {
    private val binding by viewBinding(LayoutGamesPagingFragmentBinding::bind)
    private val progressDialog = ProgressDialog.newInstance()

    private lateinit var pagingAdapter: GamesPagingAdapter
    private lateinit var viewModel: GamesPagingViewModel
    private lateinit var title: String
    private lateinit var listType: ListType

    private var resumed = false


    companion object {
        const val KEY_TITLE = "title"
        const val KEY_LIST_TYPE = "list-type"
    }

    private val itemDiffCallback = object : DiffUtil.ItemCallback<GamesPagingItemData>() {
        override fun areItemsTheSame(oldItem: GamesPagingItemData, newItem: GamesPagingItemData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GamesPagingItemData, newItem: GamesPagingItemData): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments?.getString(KEY_TITLE).orEmpty()
        listType = arguments?.getAny<ListType>(KEY_LIST_TYPE) ?: ListType.TRENDING

        viewModel = inject<GamesPagingViewModel> { parametersOf(listType) }.value
        val resourceProvider = inject<ResourceProvider>().value

        pagingAdapter = GamesPagingAdapter(resourceProvider, itemDiffCallback)
    }


    override fun onResume() {
        super.onResume()
        resumed = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupPagingRecyclerView()
        if (!resumed) {
            viewModel.fetchData()
        }
        observePagingState()
        observeData()
    }

    private fun observeData() {
        viewModel.getData().observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {
                pagingAdapter.submitData(it)
            }
        }
    }

    private fun observePagingState() {
        viewLifecycleOwner.lifecycleScope.launch {
            pagingAdapter.loadStateFlow.collectLatest {
                when (it.refresh) {
                    is LoadState.Loading -> progressDialog.showProgress(true, parentFragmentManager)
                    is LoadState.Error -> {
                        showError((it.refresh as? LoadState.Error)?.error?.message)
                    }

                    else -> progressDialog.showProgress(false, parentFragmentManager)
                }
            }
        }
    }

    private fun showError(error: String?) {
        alertDialog(requireContext()) {
            title(R.string.generic_error_title)
            description(error ?: resources.getString(R.string.generic_error_description))
            neutralText(R.string.alert_dialog_neutral_button_text)
            type(AlertDialog.Type.ERROR)
        }
    }

    private fun setupPagingRecyclerView() {
        binding.pagingRecyclerView.run {
            val preloadLinearLayoutManager = PreloadLinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            preloadLinearLayoutManager.setPreloadItemCount(10)
            this.layoutManager = preloadLinearLayoutManager
            this.adapter = pagingAdapter
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            CustomLinearSnapHelper().attachToRecyclerView(this)
        }
    }

    override fun getTitle(): String = title

    override fun initialToolbarAlpha(): Float = 1f

    override fun getToolbarType(): ToolbarType = ToolbarType.BACK
}