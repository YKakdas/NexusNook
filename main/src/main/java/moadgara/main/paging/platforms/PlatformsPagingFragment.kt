package moadgara.main.paging.platforms

import android.os.Bundle
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import moadgara.base.util.tryCastNotNull
import moadgara.base.viewBinding
import moadgara.main.R
import moadgara.main.databinding.LayoutPlatformsPagingFragmentBinding
import moadgara.main.paging.BasePagingFragment
import moadgara.main.paging.BasePagingViewModel
import moadgara.main.paging.PagingItemData
import moadgara.uicomponent.CustomLinearSnapHelper
import moadgara.uicomponent.PreloadLinearLayoutManager
import moadgara.uicomponent.adapter.PagingGenericAdapter
import moadgara.uicomponent.adapter.pagingGenericAdapter
import moadgara.uicomponent.overlay.ToolbarType
import org.koin.android.ext.android.inject

class PlatformsPagingFragment : BasePagingFragment(R.layout.layout_platforms_paging_fragment) {
    private val binding by viewBinding(LayoutPlatformsPagingFragmentBinding::bind)
    private val viewModel: PlatformsPagingViewModel by inject()

    private lateinit var pagingAdapter: PagingGenericAdapter<PlatformsPagingItemData>
    private lateinit var title: String

    companion object {
        const val KEY_TITLE = "title"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments?.getString(KEY_TITLE).orEmpty()
    }

    override fun getPagingAdapter(): PagingDataAdapter<PagingItemData, RecyclerView.ViewHolder> {
        pagingAdapter = pagingGenericAdapter {
            diffCallback(super.getItemDiffCallback().tryCastNotNull())
            itemLayoutResource(R.layout.layout_platform_paging_item_view)
        }
        return pagingAdapter.tryCastNotNull()
    }

    override fun getBasePagingViewModel(): BasePagingViewModel = viewModel

    override fun setupRecyclerView() {
        binding.pagingRecyclerView.run {
            val preloadLinearLayoutManager = PreloadLinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            preloadLinearLayoutManager.setPreloadItemCount(5)
            this.layoutManager = preloadLinearLayoutManager
            this.adapter = pagingAdapter
            setHasFixedSize(true)
            setItemViewCacheSize(5)
            CustomLinearSnapHelper().attachToRecyclerView(this)
        }
    }

    override fun getTitle(): String = title

    override fun initialToolbarAlpha(): Float = 1f

    override fun getToolbarType(): ToolbarType = ToolbarType.BACK
}