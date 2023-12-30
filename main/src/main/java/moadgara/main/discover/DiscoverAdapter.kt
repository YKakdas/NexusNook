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
                return oldItem === newItem
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
        prefetchThreshold = (data?.size?.minus(1)) ?: 0
        prefetchImages()
    }

    private fun prefetchImages() {
        for (position in 0..prefetchThreshold) {
            data?.get(position)?.imageUrl?.let { imageUrl ->
                val request = ImageRequest.Builder(context)
                  .data(imageUrl)
                  .listener(
                    onSuccess = { _, _ -> coilOnSuccess() },
                    onError = { _, _ -> coilOnFailure() },
                    onCancel = { _ -> coilOnFailure() })
                  .build()

                imageLoader.enqueue(request)
            }
        }
    }

    private val coilOnSuccess = {
        prefetchCount++
        if (prefetchCount >= prefetchThreshold) {
            prefetchCallback?.invoke()
        }
    }

    private val coilOnFailure = { prefetchThreshold-- }

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