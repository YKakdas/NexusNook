package moadgara.main

import android.content.res.ColorStateList
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.core.widget.ImageViewCompat
import androidx.lifecycle.ViewModel
import moadgara.base.ResourceProvider
import moadgara.uicomponent.R as uiComponentR

class MainViewModel(
    private val navigator: MainNavigator,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private var isFabOpen = false

    fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.discover -> navigator.navigateToDiscoverFragment()
            R.id.search -> navigator.navigateToSearchFragment()
            R.id.favorites -> navigator.navigateToFavoritesFragment()
        }
        return true
    }

    fun onFabClicked(view: View) {
        val angle = if (isFabOpen) -180f else 180f
        isFabOpen = !isFabOpen

        val tintColor =
            if (isFabOpen) uiComponentR.color.colorPrimary else uiComponentR.color.fountain_blue

        view.animate().rotationBy(angle).duration = 500L
        ImageViewCompat.setImageTintList(
            view as ImageView, ColorStateList.valueOf(resourceProvider.getColor(tintColor))
        )
    }
}