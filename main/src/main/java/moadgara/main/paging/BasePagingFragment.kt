package moadgara.main.paging

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import moadgara.main.R
import moadgara.uicomponent.AlertDialog
import moadgara.uicomponent.BaseFragment
import moadgara.uicomponent.alertDialog
import moadgara.uicomponent.custom_view.ProgressDialog

abstract class BasePagingFragment(@LayoutRes layoutId: Int) : BaseFragment(layoutId) {

    abstract fun getPagingAdapter(): PagingDataAdapter<PagingItemData, RecyclerView.ViewHolder>

    abstract fun getBasePagingViewModel(): BasePagingViewModel

    abstract fun setupRecyclerView()

    private var resumed = false
    private lateinit var viewModel: BasePagingViewModel
    private lateinit var pagingAdapter: PagingDataAdapter<PagingItemData, RecyclerView.ViewHolder>

    private val progressDialog = ProgressDialog.newInstance()

    private val itemDiffCallback = object : DiffUtil.ItemCallback<PagingItemData>() {
        override fun areItemsTheSame(oldItem: PagingItemData, newItem: PagingItemData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PagingItemData, newItem: PagingItemData): Boolean {
            return oldItem.id == newItem.id
        }
    }

    fun getItemDiffCallback() = itemDiffCallback

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = getBasePagingViewModel()
        pagingAdapter = getPagingAdapter()

        setupRecyclerView()

        if (!resumed) {
            getBasePagingViewModel().fetchData()
        }
        observePagingState()
        observeData()
    }

    override fun onResume() {
        super.onResume()
        resumed = true
    }

    private fun observeData() {
        viewModel.getObserver().observe(viewLifecycleOwner) {
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
}