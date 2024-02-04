package moadgara.uicomponent.adapter

fun interface ItemClickListener<T> {
    fun onItemClicked(listItem: T)
}