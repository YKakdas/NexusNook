package moadgara.main.discover

import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Space
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import moadgara.base.extension.addViewCheckIfExists
import moadgara.base.extension.observeOnce
import moadgara.base.extension.toPx
import moadgara.base.util.ViewUtil
import moadgara.main.R
import moadgara.main.databinding.FragmentDiscoverBinding
import moadgara.main.databinding.LayoutPreviewListBinding
import moadgara.uicomponent.enforceSingleScrollDirection
import moadgara.uicomponent.CustomLinearSnapHelper
import moadgara.uicomponent.PreloadLinearLayoutManager
import moadgara.uicomponent.adapter.GenericAdapter
import moadgara.uicomponent.adapter.genericAdapter


class DiscoverViewHelper(private val fragment: DiscoverFragment) {

    private lateinit var dataObservers: List<LiveData<PreviewListViewData>>
    private lateinit var listMetaData: List<PreviewListMetaData>
    private lateinit var binding: FragmentDiscoverBinding

    fun setDataObservers(dataObservers: List<LiveData<PreviewListViewData>>) = apply {
        this.dataObservers = dataObservers
    }

    fun setListMetaData(listMetaData: List<PreviewListMetaData>) = apply {
        this.listMetaData = listMetaData
    }

    fun setBinding(binding: FragmentDiscoverBinding) = apply {
        this.binding = binding
    }

    fun inflateViews() {
        dataObservers.forEachIndexed { index, liveData ->
            populateView(listMetaData[index], liveData, index)
        }
    }

    private fun populateView(
        metaData: PreviewListMetaData,
        liveData: LiveData<PreviewListViewData>,
        index: Int
    ) {
        val root = binding.inflateViewRoot
        val listViewBinding: LayoutPreviewListBinding =
            DataBindingUtil.inflate(LayoutInflater.from(root.context), R.layout.layout_preview_list, root, false)
        listViewBinding.data = metaData

        val space = addSpace(root, index)

        val adapter = genericAdapter { diffCallback(itemDiffCallback) }
        setupRecyclerView(listViewBinding.recyclerView, adapter)

        liveData.observeOnce(fragment.viewLifecycleOwner) {
            if (it != null) {
                adapter.submitList(it.list)
                root.post {
                    val child = root.indexOfChild(space)
                    root.addViewCheckIfExists(listViewBinding.root, child + 1)
                }
            }
        }
    }

    private fun addSpace(root: LinearLayout, index: Int): Space {
        val space = ViewUtil.createSpace(
            fragment.requireContext(),
            height = moadgara.uicomponent.R.dimen.margin_high.toPx(fragment.resources)
        )
        return space.also { root.addView(space, index) }
    }

    private val itemDiffCallback = object : DiffUtil.ItemCallback<PreviewListItemData>() {
        override fun areItemsTheSame(oldItem: PreviewListItemData, newItem: PreviewListItemData): Boolean {
            return oldItem.gameTitle == newItem.gameTitle
        }

        override fun areContentsTheSame(oldItem: PreviewListItemData, newItem: PreviewListItemData): Boolean {
            return oldItem.gameTitle == newItem.gameTitle
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, adapter: GenericAdapter<PreviewListItemData>) {
        recyclerView.apply {
            val preloadLinearLayoutManager = PreloadLinearLayoutManager(fragment.requireContext(), RecyclerView.HORIZONTAL, false)
            preloadLinearLayoutManager.setPreloadItemCount(6)
            this.layoutManager = preloadLinearLayoutManager
            this.adapter = adapter
            setHasFixedSize(true)
            setItemViewCacheSize(6)
            CustomLinearSnapHelper().attachToRecyclerView(this)
            enforceSingleScrollDirection()
        }
    }

}