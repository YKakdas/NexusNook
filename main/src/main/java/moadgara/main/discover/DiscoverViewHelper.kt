package moadgara.main.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.request.CachePolicy
import moadgara.base.util.DimensionUtil
import moadgara.base.util.ViewUtil
import moadgara.main.R
import moadgara.main.databinding.LayoutPreviewListBinding
import moadgara.main.databinding.LayoutPreviewListShimmerBinding
import moadgara.uicomponent.CustomLinearSnapHelper


class DiscoverViewHelper(private val fragment: DiscoverFragment) {

    private lateinit var dataObservers: List<LiveData<PreviewList>>
    private lateinit var listMetaData: List<PreviewListMetaData>
    private lateinit var rootView: ViewGroup
    private var imageWidth: Int = 0
    private var imageHeight: Int = 0

    init {
        imageWidth = DimensionUtil.dpToPx(
          resourceId = moadgara.uicomponent.R.dimen.preview_list_item_view_width,
          resources = fragment.resources
        )
        imageHeight = DimensionUtil.dpToPx(
          resourceId = moadgara.uicomponent.R.dimen.preview_list_item_image_height,
          resources = fragment.resources
        )
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

        val coilImageLoader = ImageLoader.Builder(fragment.requireContext())
          .crossfade(true)
          .memoryCachePolicy(CachePolicy.ENABLED) // Enable memory caching
          .diskCachePolicy(CachePolicy.ENABLED)   // Enable disk caching
          .build()

        val adapter = DiscoverAdapter(coilImageLoader)

        actualViewBinding.recyclerView.apply {
            this.layoutManager =
              LinearLayoutManager(fragment.requireContext(), RecyclerView.HORIZONTAL, false)
            setHasFixedSize(true)
            setItemViewCacheSize(40)
            recycledViewPool.setMaxRecycledViews(R.layout.layout_preview_list, 40)
            this.adapter = adapter
        }

        CustomLinearSnapHelper().attachToRecyclerView(actualViewBinding.recyclerView)
        liveData.observe(fragment.viewLifecycleOwner) {
            if (it != null) {
                val index = rootView.indexOfChild(shimmerViewBinding.root)
                adapter.setData(it.list)
                adapter.submitList(it.list)
                adapter.setPrefetchCallback {
                    rootView.addView(actualViewBinding.root, index)
                    rootView.removeView(shimmerViewBinding.root)
                }
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