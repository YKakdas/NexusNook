package moadgara.uicomponent.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class GenericAdapter<T : Any> internal constructor(callback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, GenericAdapter<T>.ViewHolder>(callback) {

    @LayoutRes
    internal var itemLayoutResource: Int = 0
    internal var selectionEnabled = false
    internal var clickListener: ItemClickListener<T>? = null

    internal lateinit var binder: AdapterDataBinder

    private var selectedItemPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), viewType, parent, false
        )
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
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    if (selectionEnabled) {
                        changeSelection(adapterPosition)
                    }

                    clickListener?.onItemClicked(getItem(adapterPosition))
                }
            }
        }

        fun bindData(data: T) {
            binder.bind(binding, data)
            binding.root.isSelected = selectedItemPosition == adapterPosition
            binding.executePendingBindings()
        }
    }
}

