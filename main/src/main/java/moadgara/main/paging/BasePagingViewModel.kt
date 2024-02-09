package moadgara.main.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData

abstract class BasePagingViewModel : ViewModel() {
    abstract fun fetchData()

    abstract fun getObserver(): LiveData<out PagingData<PagingItemData>>
}

enum class PagingViewModelType {
    GAMES,
    PLATFORMS,
    GENRES
}