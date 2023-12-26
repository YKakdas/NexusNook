package moadgara.main.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import moadgara.base.util.DimensionUtil
import moadgara.base.util.ViewUtil
import moadgara.main.R
import moadgara.main.databinding.LayoutPreviewListBinding
import moadgara.main.databinding.LayoutPreviewListShimmerBinding
import moadgara.uicomponent.adapter.genericAdapter
import moadgara.uicomponent.glide.GlideUtil


class DiscoverViewHelper(private val fragment: DiscoverFragment) {

    private lateinit var dataObservers: List<LiveData<PreviewList>>
    private lateinit var listMetaData: List<PreviewListMetaData>
    private lateinit var rootView: ViewGroup
    private var imageWidth: Int = 0
    private var imageHeight: Int = 0

    init {
        imageWidth = DimensionUtil.dpToPx(value = 100, resources = fragment.resources)
        imageHeight = DimensionUtil.dpToPx(value = 100, resources = fragment.resources)
    }

    fun setDataObservers(dataObservers: List<LiveData<PreviewList>>) = apply {
        this.dataObservers = dataObservers
    }

    fun setListMetaData(listMetaData: List<PreviewListMetaData>) = apply {
        this.listMetaData = listMetaData
    }

    fun setRootView(rootView: ViewGroup) = apply {
        this.rootView = rootView
    }

    fun inflateViews() {
        dataObservers.forEachIndexed { index, liveData ->
            populateView(listMetaData[index], liveData)
        }
    }

    private fun populateView(
        metaData: PreviewListMetaData,
        liveData: LiveData<PreviewList>
    ) {
        val actualViewBinding = inflatePreviewListLayout<LayoutPreviewListBinding>(rootView)
        val shimmerViewBinding = inflatePreviewListLayout<LayoutPreviewListShimmerBinding>(rootView)
        val space = ViewUtil.createSpace(
            fragment.requireContext(),
            height = DimensionUtil.dpToPx(
                resourceId = moadgara.uicomponent.R.dimen.margin_high,
                resources = fragment.resources
            )
        )

        actualViewBinding.data = metaData

        rootView.addView(shimmerViewBinding.root)
        rootView.addView(space)

        val adapter = genericAdapter<PreviewListItemData> {}

        actualViewBinding.recyclerView.apply {
            this.layoutManager =
                LinearLayoutManager(fragment.requireContext(), RecyclerView.HORIZONTAL, false)
            setHasFixedSize(true)
            setItemViewCacheSize(40)
            recycledViewPool.setMaxRecycledViews(R.layout.layout_preview_list, 10)
            this.adapter = adapter
        }

        LinearSnapHelper().attachToRecyclerView(actualViewBinding.recyclerView)
        liveData.observe(fragment.viewLifecycleOwner) {
            val index = rootView.indexOfChild(shimmerViewBinding.root)
            rootView.removeView(shimmerViewBinding.root)

            if (it != null && !it.list.isNullOrEmpty()) {
                val imageUrls = it.list.mapNotNull { data -> data.imageUrl }

                val recyclerViewPreloader = GlideUtil.getRecyclerViewPreloader(
                    fragment, imageUrls, imageWidth, imageHeight
                )
                actualViewBinding.recyclerView.addOnScrollListener(recyclerViewPreloader)

                rootView.addView(actualViewBinding.root, index)
                adapter.submitList(it.list)
            }
        }
    }

    private inline fun <reified T : ViewDataBinding> inflatePreviewListLayout(parent: ViewGroup): T {
        val layout = if (T::class == LayoutPreviewListBinding::class) {
            R.layout.layout_preview_list
        } else {
            R.layout.layout_preview_list_shimmer
        }
        return DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)
    }

}