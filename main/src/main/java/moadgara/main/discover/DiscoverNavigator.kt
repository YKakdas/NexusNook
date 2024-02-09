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
import moadgara.main.paging.CommonPagingFragment
import moadgara.main.paging.PagingViewModelType
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
        navigateToAllCommon(name, PagingViewModelType.GENRES)
    }

    fun navigateToGenreDetail(name: String, id: Int?) {
        val listType = ListType.GENRES
        listType.additionalParameters = listOf(id?.orZero.toString())
        navigateToAllGamesPage(name, listType)
    }

    fun navigateToAllPlatforms(name: String) {
        navigateToAllCommon(name, PagingViewModelType.PLATFORMS)
    }

    fun navigateToPlatformDetail(name: String, id: Int?) {
        val listType = ListType.PLATFORMS
        listType.additionalParameters = listOf(id?.orZero.toString())
        navigateToAllGamesPage(name, listType)
    }

    fun navigateToAllPublishers(name: String) {
        navigateToAllCommon(name, PagingViewModelType.PUBLISHERS)
    }

    fun navigateToPublisherDetail(name: String, id: Int?) {
        val listType = ListType.PUBLISHERS
        listType.additionalParameters = listOf(id?.orZero.toString())
        navigateToAllGamesPage(name, listType)
    }

    fun navigateToAllStores(name: String) {
        navigateToAllCommon(name, PagingViewModelType.STORES)
    }

    fun navigateToStoreDetail(name: String, id: Int?) {
        val listType = ListType.STORES
        listType.additionalParameters = listOf(id?.orZero.toString())
        navigateToAllGamesPage(name, listType)
    }

    fun navigateToAllCreators(name: String) {
        navigateToAllCommon(name, PagingViewModelType.CREATORS)
    }

    fun navigateToCreatorDetail(name: String, id: Int?) {
        val listType = ListType.CREATORS
        listType.additionalParameters = listOf(id?.orZero.toString())
        navigateToAllGamesPage(name, listType)
    }

    fun navigateToAllDevelopers(name: String) {
        navigateToAllCommon(name, PagingViewModelType.DEVELOPERS)
    }

    fun navigateToDeveloperDetail(name: String, id: Int?) {
        val listType = ListType.DEVELOPERS
        listType.additionalParameters = listOf(id?.orZero.toString())
        navigateToAllGamesPage(name, listType)
    }

    fun navigateToAllTags(name: String) {
        navigateToAllCommon(name, PagingViewModelType.TAGS)
    }

    fun navigateToTagDetail(name: String, id: Int?) {
        val listType = ListType.TAGS
        listType.additionalParameters = listOf(id?.orZero.toString())
        navigateToAllGamesPage(name, listType)
    }

    private fun navigateToAllCommon(name: String, pagingViewModelType: PagingViewModelType) {
        val bundle = Bundle()
        bundle.putString(CommonPagingFragment.KEY_TITLE, name)
        bundle.putAny(CommonPagingFragment.KEY_VIEW_MODEL_TYPE, pagingViewModelType)
        activityWeakReference.get()?.supportFragmentManager?.let {
            OverlayBaseFragment.startOrAdd(it, CommonPagingFragment::class.java, bundle)
        }
    }

}