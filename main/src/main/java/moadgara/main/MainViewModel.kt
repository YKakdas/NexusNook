package moadgara.main

import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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