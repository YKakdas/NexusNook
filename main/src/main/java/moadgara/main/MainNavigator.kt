package moadgara.main

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import moadgara.main.discover.DiscoverFragment
import moadgara.main.favorites.FavoritesFragment
import moadgara.main.search.SearchFragment
import java.lang.ref.WeakReference

class MainNavigator(private var activity: Activity?) {
    private val activityWeakReference = WeakReference(activity as FragmentActivity)

    fun navigateToDiscoverFragment() {
        activityWeakReference.get()?.supportFragmentManager?.commit {
            val fragment = DiscoverFragment.newInstance()
            replace(R.id.main_container, fragment, fragment::class.java.simpleName)
        }
    }

    fun navigateToSearchFragment() {
        activityWeakReference.get()?.supportFragmentManager?.commit {
            val fragment = SearchFragment.newInstance()
            replace(R.id.main_container, fragment, fragment::class.java.simpleName)
        }
    }

    fun navigateToFavoritesFragment() {
        activityWeakReference.get()?.supportFragmentManager?.commit {
            val fragment = FavoritesFragment.newInstance()
            replace(R.id.main_container, fragment, fragment::class.java.simpleName)
        }
    }
}