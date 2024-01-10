package moadgara.main.discover

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import moadgara.main.games_detail.GameDetailFragment
import moadgara.main.overlay.OverlayBaseFragment
import java.lang.ref.WeakReference

class DiscoverNavigator(activity: Activity?) {
    private val activityWeakReference = WeakReference(activity as FragmentActivity)

    fun navigateToGameDetailPage(id: Int?) {
        Toast.makeText(activityWeakReference.get()?.applicationContext, id.toString(), Toast.LENGTH_SHORT).show()
        activityWeakReference.get()?.supportFragmentManager?.let {
            OverlayBaseFragment.startOrAdd(it, GameDetailFragment::class.java)
        }

    }

    fun navigateToAllGamesPage(name: String) {
        Toast.makeText(activityWeakReference.get()?.applicationContext, name, Toast.LENGTH_SHORT).show()
    }

    fun navigateToAllGenres(name: String) {
        Toast.makeText(activityWeakReference.get()?.applicationContext, name, Toast.LENGTH_SHORT).show()
    }

    fun navigateToGenreDetail(id: Int?) {
        Toast.makeText(activityWeakReference.get()?.applicationContext, id.toString(), Toast.LENGTH_SHORT).show()
    }

    fun navigateToAllPlatforms(name: String) {
        Toast.makeText(activityWeakReference.get()?.applicationContext, name, Toast.LENGTH_SHORT).show()
    }

    fun navigateToPlatformDetail(id: Int?) {
        Toast.makeText(activityWeakReference.get()?.applicationContext, id.toString(), Toast.LENGTH_SHORT).show()
    }

    fun navigateToAllPublishers(name: String) {
        Toast.makeText(activityWeakReference.get()?.applicationContext, name, Toast.LENGTH_SHORT).show()
    }

    fun navigateToPublisherDetail(id: Int?) {
        Toast.makeText(activityWeakReference.get()?.applicationContext, id.toString(), Toast.LENGTH_SHORT).show()
    }

    fun navigateToAllStores(name: String) {
        Toast.makeText(activityWeakReference.get()?.applicationContext, name, Toast.LENGTH_SHORT).show()
    }

    fun navigateToStoreDetail(id: Int?) {
        Toast.makeText(activityWeakReference.get()?.applicationContext, id.toString(), Toast.LENGTH_SHORT).show()
    }

    fun navigateToAllCreators(name: String) {
        Toast.makeText(activityWeakReference.get()?.applicationContext, name, Toast.LENGTH_SHORT).show()
    }

    fun navigateToCreatorDetail(id: Int?) {
        Toast.makeText(activityWeakReference.get()?.applicationContext, id.toString(), Toast.LENGTH_SHORT).show()
    }

    fun navigateToAllDevelopers(name: String) {
        Toast.makeText(activityWeakReference.get()?.applicationContext, name, Toast.LENGTH_SHORT).show()
    }

    fun navigateToDeveloperDetail(id: Int?) {
        Toast.makeText(activityWeakReference.get()?.applicationContext, id.toString(), Toast.LENGTH_SHORT).show()
    }

    fun navigateToAllTags(name: String) {
        Toast.makeText(activityWeakReference.get()?.applicationContext, name, Toast.LENGTH_SHORT).show()
    }

    fun navigateToTagDetail(id: Int?) {
        Toast.makeText(activityWeakReference.get()?.applicationContext, id.toString(), Toast.LENGTH_SHORT).show()
    }
}