package moadgara.main.paging

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import moadgara.base.extension.getAny
import moadgara.base.viewBinding
import moadgara.domain.ListType
import moadgara.main.R
import moadgara.main.databinding.LayoutGenericPagingFragmentBinding
import moadgara.uicomponent.BaseFragment
import moadgara.uicomponent.CustomLinearSnapHelper
import moadgara.uicomponent.PreloadLinearLayoutManager
import moadgara.uicomponent.adapter.PagingGenericAdapter
import moadgara.uicomponent.adapter.pagingGenericAdapter
import moadgara.uicomponent.overlay.ToolbarFragment
import moadgara.uicomponent.overlay.ToolbarType
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class GenericPagingFragment : BaseFragment(R.layout.layout_generic_paging_fragment), ToolbarFragment {
    private val binding by viewBinding(LayoutGenericPagingFragmentBinding::bind)

    private lateinit var pagingAdapter: PagingGenericAdapter<GenericPagingItemData>

    private lateinit var viewModel: GenericPagingViewModel
    private lateinit var title: String
    private lateinit var listType: ListType

    private var resumed = false

    companion object {
        const val KEY_TITLE = "title"
        const val KEY_LIST_TYPE = "list-type"
    }

    private val itemDiffCallback = object : DiffUtil.ItemCallback<GenericPagingItemData>() {
        override fun areItemsTheSame(oldItem: GenericPagingItemData, newItem: GenericPagingItemData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GenericPagingItemData, newItem: GenericPagingItemData): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments?.getString(KEY_TITLE).orEmpty()
        listType = arguments?.getAny<ListType>(KEY_LIST_TYPE) ?: ListType.TRENDING

        viewModel = inject<GenericPagingViewModel> { parametersOf(listType) }.value
        pagingAdapter = pagingGenericAdapter { diffCallback(itemDiffCallback) }
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
        observeData()
    }

    private fun observeData() {
        viewModel.getData().observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {
                pagingAdapter.submitData(it)
            }
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

    override fun onBackPressed(): Boolean = false

    override fun getToolbarType(): ToolbarType = ToolbarType.BACK
}