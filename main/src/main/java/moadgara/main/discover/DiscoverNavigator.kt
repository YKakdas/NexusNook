package moadgara.main.discover

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import java.lang.ref.WeakReference

class DiscoverNavigator(activity: Activity?) {
    private val activityWeakReference = WeakReference(activity as FragmentActivity)

    fun navigateToGameDetailPage(name: String?) {
        Toast.makeText(activityWeakReference.get()?.applicationContext, name, Toast.LENGTH_SHORT).show()
    }

    fun navigateToAllGamesPage(name: String) {
        Toast.makeText(activityWeakReference.get()?.applicationContext, name, Toast.LENGTH_SHORT).show()
    }

    fun navigateToAllGenres(name: String) {
        Toast.makeText(activityWeakReference.get()?.applicationContext, name, Toast.LENGTH_SHORT).show()
    }

    fun navigateToGenreDetail(name: String?) {
        Toast.makeText(activityWeakReference.get()?.applicationContext, name, Toast.LENGTH_SHORT).show()
    }

    fun navigateToAllPlatforms(name: String) {
        Toast.makeText(activityWeakReference.get()?.applicationContext, name, Toast.LENGTH_SHORT).show()
    }

    fun navigateToPlatformDetail(name: String?) {
        Toast.makeText(activityWeakReference.get()?.applicationContext, name, Toast.LENGTH_SHORT).show()
    }
}