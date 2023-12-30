package moadgara.main.discover

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.request.ImageRequest
import moadgara.main.BR
import moadgara.main.databinding.LayoutSmallCardViewListItemBinding

class DiscoverAdapter(private val imageLoader: ImageLoader) :
    ListAdapter<PreviewListItemData, DiscoverAdapter.DiscoverViewHolder>(itemDiff) {
    private lateinit var context: Context
    private var data: List<PreviewListItemData>? = null
    private var prefetchCallback: (() -> Unit)? = null
    private var prefetchThreshold = 20
    private var prefetchCount = 0

    companion object {
        private val itemDiff = object : DiffUtil.ItemCallback<PreviewListItemData>() {
            override fun areItemsTheSame(oldItem: PreviewListItemData, newItem: PreviewListItemData): Boolean {
                return oldItem.gameTitle == newItem.gameTitle
            }

            override fun areContentsTheSame(oldItem: PreviewListItemData, newItem: PreviewListItemData): Boolean {
                return oldItem.gameTitle == newItem.gameTitle
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverViewHolder {
        return DiscoverViewHolder(
            LayoutSmallCardViewListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: DiscoverViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let {
            holder.bind(it)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun getItemCount() = data?.size ?: 0

    fun setData(data: List<PreviewListItemData>?) {
        this.data = data
        prefetchImages()
    }

    private fun prefetchImages() {
        prefetchThreshold = (data?.size?.minus(1)) ?: 0
        val nextItemsToPrefetch = 0..prefetchThreshold
        nextItemsToPrefetch.forEach { prefetchPosition ->
            data?.get(prefetchPosition)?.imageUrl?.let { imageUrl ->
                val request = ImageRequest.Builder(context)
                    .data(imageUrl)
                    .listener(
                        onSuccess = { _, _ ->
                            prefetchCount++
                            if (prefetchCount >= prefetchThreshold) {
                                prefetchCallback?.invoke()
                            }
                        },
                        onError = { _, _ -> prefetchThreshold-- },
                        onCancel = { _ -> prefetchThreshold-- })
                    .build()

                imageLoader.enqueue(request)
            }
        }
    }

    fun setPrefetchCallback(prefetchCallback: () -> Unit) {
        this.prefetchCallback = prefetchCallback
    }

    class DiscoverViewHolder(val binding: LayoutSmallCardViewListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PreviewListItemData) {
            binding.setVariable(BR.data, item)
            binding.executePendingBindings()
        }
    }
    
}