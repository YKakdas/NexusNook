package moadgara.main

import android.content.res.ColorStateList
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.core.widget.ImageViewCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import moadgara.base.ResourceProvider
import moadgara.uicomponent.R as uiComponentR

class MainViewModel(
    private val navigator: MainNavigator
) : ViewModel() {

    private var isFabOpen = false
    private val onFabClicked = MutableLiveData<Boolean>()

    fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.discover -> navigator.navigateToDiscoverFragment()
            R.id.search -> navigator.navigateToSearchFragment()
            R.id.favorites -> navigator.navigateToFavoritesFragment()
        }
        return true
    }

    fun getIsFabClicked(): LiveData<Boolean> = onFabClicked

    fun onFabClicked() {
        onFabClicked.value = !isFabOpen
        isFabOpen = !isFabOpen
    }
}