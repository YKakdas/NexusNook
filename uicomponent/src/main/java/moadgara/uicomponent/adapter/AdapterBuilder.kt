package moadgara.uicomponent.adapter

import android.annotation.SuppressLint
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import moadgara.uicomponent.BR

@DslMarker
annotation class AdapterBuilderMarker

fun <T : Any> genericAdapter(builder: AdapterBuilder<T>.() -> Unit): GenericAdapter<T> {
    return AdapterBuilder<T>().apply(builder).build()
}

fun <T : Any> pagingGenericAdapter(builder: AdapterBuilder<T>.() -> Unit): PagingGenericAdapter<T> {
    return AdapterBuilder<T>().apply(builder).buildPaging()
}

@AdapterBuilderMarker
class AdapterBuilder<T : Any> {
    private var itemLayoutResource = 0
    private var bindId: Int? = null
    private var itemCallback: DiffUtil.ItemCallback<T>? = null
    private var selectionEnabled = false
    private var itemClickListener: ItemClickListener<T>? = null

    private val defaultCallback: DiffUtil.ItemCallback<T> = object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem === newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }
    }

    /**
     * Layout resource id to use for all item views. If supplied data extends [ListItem] class then layout id in this object will be used.
     */
    fun itemLayoutResource(@LayoutRes resource: Int) = apply { itemLayoutResource = resource }

    /**
     * Custom databinding variable id to bind given data, otherwise by default it will be named to "data".
     */
    fun bindToId(id: Int) = apply { bindId = id }

    /**
     * Callback to be triggered when an item view clicked in the list.
     */
    fun itemClickListener(listener: ItemClickListener<T>) =
        apply { this.itemClickListener = listener }

    /**
     * Enables selection mode. Root view of item view's "state_selected" will be true.
     */
    fun selectionEnabled(selectionEnabled: Boolean) =
        apply { this.selectionEnabled = selectionEnabled }

    /**
     * [DiffUtil.ItemCallback] to be used when new data set submitted.
     * If a callback is not supplied, default callback is used. Default callback checks only object equalities.
     */
    fun diffCallback(itemCallback: DiffUtil.ItemCallback<T>) =
        apply { this.itemCallback = itemCallback }

    internal fun build(): GenericAdapter<T> {
        val adapter = GenericAdapter(itemCallback ?: defaultCallback)
        adapter.itemLayoutResource = itemLayoutResource
        adapter.selectionEnabled = selectionEnabled
        adapter.binder = AdapterDataBinder(bindId ?: BR.data)
        adapter.clickListener = itemClickListener

        return adapter
    }

    internal fun buildPaging(): PagingGenericAdapter<T> {
        val adapter = PagingGenericAdapter(itemCallback ?: defaultCallback)
        adapter.itemLayoutResource = itemLayoutResource
        adapter.selectionEnabled = selectionEnabled
        adapter.binder = AdapterDataBinder(bindId ?: BR.data)
        adapter.clickListener = itemClickListener

        return adapter
    }
}