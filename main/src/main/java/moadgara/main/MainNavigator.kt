package moadgara.main

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import moadgara.main.discover.DiscoverFragment
import moadgara.main.favorites.FavoritesFragment
import moadgara.main.search.SearchFragment
import java.lang.ref.WeakReference

class MainNavigator(activity: Activity?) {
    private val activityWeakReference = WeakReference(activity as FragmentActivity)

    private lateinit var discoverFragment: DiscoverFragment
    private lateinit var searchFragment: SearchFragment
    private lateinit var favoritesFragment: FavoritesFragment

    fun navigateToDiscoverFragment() {
        if (!::discoverFragment.isInitialized) {
            discoverFragment = DiscoverFragment.newInstance()
        }

        activityWeakReference.get()?.supportFragmentManager?.commit {
            replace(R.id.main_container, discoverFragment, discoverFragment::class.java.simpleName)
        }
    }

    fun navigateToSearchFragment() {
        if (!::searchFragment.isInitialized) {
            searchFragment = SearchFragment.newInstance()
        }

        activityWeakReference.get()?.supportFragmentManager?.commit {
            replace(R.id.main_container, searchFragment, searchFragment::class.java.simpleName)
        }
    }

    fun navigateToFavoritesFragment() {
        if (!::favoritesFragment.isInitialized) {
            favoritesFragment = FavoritesFragment.newInstance()
        }

        activityWeakReference.get()?.supportFragmentManager?.commit {
            replace(
              R.id.main_container,
              favoritesFragment,
              favoritesFragment::class.java.simpleName
            )
        }
    }
}