package moadgara.main.paging

import android.os.Bundle
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import moadgara.base.extension.getAny
import moadgara.base.util.tryCastNotNull
import moadgara.base.viewBinding
import moadgara.main.R
import moadgara.main.databinding.LayoutCommonPagingFragmentBinding
import moadgara.main.paging.genres.GenresPagingViewModel
import moadgara.main.paging.platforms.PlatformsPagingViewModel
import moadgara.uicomponent.CustomLinearSnapHelper
import moadgara.uicomponent.PreloadLinearLayoutManager
import moadgara.uicomponent.adapter.PagingGenericAdapter
import moadgara.uicomponent.adapter.pagingGenericAdapter
import moadgara.uicomponent.overlay.ToolbarType
import org.koin.androidx.viewmodel.ext.android.getViewModel

class CommonPagingFragment : BasePagingFragment(R.layout.layout_common_paging_fragment) {
    private lateinit var pagingAdapter: PagingGenericAdapter<CommonPagingItemData>
    private lateinit var title: String

    private var viewModelType: PagingViewModelType? = PagingViewModelType.GAMES
    private val binding by viewBinding(LayoutCommonPagingFragmentBinding::bind)

    companion object {
        const val KEY_TITLE = "title"
        const val KEY_VIEW_MODEL_TYPE = "view-model-type"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments?.getString(KEY_TITLE).orEmpty()
        viewModelType = arguments?.getAny<PagingViewModelType>(KEY_VIEW_MODEL_TYPE)
    }

    override fun getPagingAdapter(): PagingDataAdapter<PagingItemData, RecyclerView.ViewHolder> {
        pagingAdapter = pagingGenericAdapter {
            diffCallback(super.getItemDiffCallback().tryCastNotNull())
            itemLayoutResource(R.layout.layout_common_paging_item_view)
        }
        return pagingAdapter.tryCastNotNull()
    }

    override fun getBasePagingViewModel(): BasePagingViewModel {
        return when (viewModelType) {
            PagingViewModelType.PLATFORMS -> getViewModel<PlatformsPagingViewModel>()
            PagingViewModelType.GENRES -> getViewModel<GenresPagingViewModel>()
            else -> throw IllegalArgumentException("Illegal paging view model argument!")
        }
    }

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

    override fun getToolbarType(): ToolbarType = ToolbarType.AUTO
}