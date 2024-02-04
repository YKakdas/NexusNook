package moadgara.main.discover

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import moadgara.base.extension.orZero
import moadgara.base.extension.putAny
import moadgara.data.games.entity.GameDetailResponse
import moadgara.domain.ListType
import moadgara.main.games_detail.GameDetailsFragment
import moadgara.main.games_detail.GameDetailsFragment.Companion.KEY_GAME_ID
import moadgara.main.games_detail.GameDetailsFragment.Companion.KEY_GAME_NAME
import moadgara.main.games_detail.GameDetailsFragment.Companion.KEY_GAME_RESPONSE
import moadgara.main.paging.games.GamesPagingFragment
import moadgara.uicomponent.overlay.OverlayBaseFragment
import java.lang.ref.WeakReference

class DiscoverNavigator(activity: Activity?) {
    private val activityWeakReference = WeakReference(activity as FragmentActivity)

    fun navigateToGameDetailPage(id: Int?, name: String?, response: GameDetailResponse?) {
        Toast.makeText(activityWeakReference.get()?.applicationContext, id.toString(), Toast.LENGTH_SHORT).show()
        val bundle = Bundle()
        bundle.putInt(KEY_GAME_ID, id.orZero)
        bundle.putString(KEY_GAME_NAME, name.orEmpty())
        bundle.putAny(KEY_GAME_RESPONSE, response)
        activityWeakReference.get()?.supportFragmentManager?.let {
            OverlayBaseFragment.startOrAdd(it, GameDetailsFragment::class.java, bundle)
        }
    }

    fun navigateToAllGamesPage(name: String, listType: ListType) {
        val bundle = Bundle()
        bundle.putString(GamesPagingFragment.KEY_TITLE, name)
        bundle.putAny(GamesPagingFragment.KEY_LIST_TYPE, listType)
        activityWeakReference.get()?.supportFragmentManager?.let {
            OverlayBaseFragment.startOrAdd(it, GamesPagingFragment::class.java, bundle)
        }
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