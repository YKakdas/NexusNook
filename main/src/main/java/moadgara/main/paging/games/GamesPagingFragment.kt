package moadgara.main.paging.games

import android.os.Bundle
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import moadgara.base.extension.getAny
import moadgara.base.util.ResourceProvider
import moadgara.base.util.tryCastNotNull
import moadgara.base.viewBinding
import moadgara.domain.ListType
import moadgara.main.R
import moadgara.main.databinding.LayoutGamesPagingFragmentBinding
import moadgara.main.paging.BasePagingFragment
import moadgara.main.paging.BasePagingViewModel
import moadgara.main.paging.PagingItemData
import moadgara.uicomponent.CustomLinearSnapHelper
import moadgara.uicomponent.PreloadLinearLayoutManager
import moadgara.uicomponent.overlay.ToolbarType
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class GamesPagingFragment : BasePagingFragment(R.layout.layout_games_paging_fragment) {
    private val binding by viewBinding(LayoutGamesPagingFragmentBinding::bind)
    private val viewModel by inject<GamesPagingViewModel> { parametersOf(listType) }

    private lateinit var pagingAdapter: GamesPagingAdapter
    private lateinit var title: String
    private lateinit var listType: ListType

    companion object {
        const val KEY_TITLE = "title"
        const val KEY_LIST_TYPE = "list-type"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments?.getString(KEY_TITLE).orEmpty()
        listType = arguments?.getAny<ListType>(KEY_LIST_TYPE) ?: ListType.TRENDING
    }

    override fun getBasePagingViewModel(): BasePagingViewModel = viewModel

    override fun getPagingAdapter(): PagingDataAdapter<PagingItemData, RecyclerView.ViewHolder> {
        val resourceProvider = inject<ResourceProvider>().value
        pagingAdapter = GamesPagingAdapter(resourceProvider, super.getItemDiffCallback().tryCastNotNull())
        return pagingAdapter.tryCastNotNull()
    }

    override fun setupRecyclerView() {
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

    override fun getToolbarType(): ToolbarType = ToolbarType.AUTO
}