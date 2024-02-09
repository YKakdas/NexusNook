package moadgara.uicomponent.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class PagingGenericAdapter<T : Any> internal constructor(callback: DiffUtil.ItemCallback<T>) :
    PagingDataAdapter<T, PagingGenericAdapter<T>.ViewHolder>(callback) {

    @LayoutRes
    internal var itemLayoutResource: Int = 0
    internal var selectionEnabled = false
    internal var clickListener: ItemClickListener<T>? = null

    internal lateinit var binder: AdapterDataBinder

    private var selectedItemPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        if (item is GenericListItem) {
            return item.layout
        }

        return itemLayoutResource
    }

    fun clearSelection() {
        changeSelection(RecyclerView.NO_POSITION)
    }

    fun selectPosition(position: Int) {
        changeSelection(position)
    }

    private fun changeSelection(position: Int) {
        if (selectedItemPosition == position) {
            return
        }
        selectedItemPosition = position
        if (selectedItemPosition != RecyclerView.NO_POSITION) {
            notifyItemChanged(selectedItemPosition)
        }
    }

    inner class ViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                if (absoluteAdapterPosition != RecyclerView.NO_POSITION) {
                    if (selectionEnabled) {
                        changeSelection(absoluteAdapterPosition)
                    }

                    val item = getItem(absoluteAdapterPosition)
                    item?.let { clickListener?.onItemClicked(it) }
                }
            }
        }

        fun bindData(data: T?) {
            data?.let { binder.bind(binding, it) }
            binding.root.isSelected = selectedItemPosition == absoluteAdapterPosition
            binding.executePendingBindings()
        }
    }
}