package moadgara.main.games_detail

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import moadgara.main.screenshot.SingleImageViewerFragment
import moadgara.uicomponent.overlay.OverlayBaseFragment
import java.lang.ref.WeakReference

class GameDetailsNavigator(activity: Activity?) {
    private val activityWeakReference = WeakReference(activity as FragmentActivity)

    fun navigateToSingleImageViewer(imageUrl: String) {
        val bundle = Bundle()
        bundle.putString(SingleImageViewerFragment.KEY_IMAGE_URL, imageUrl)
        activityWeakReference.get()?.supportFragmentManager?.let {
            OverlayBaseFragment.startOrAdd(it, SingleImageViewerFragment::class.java, bundle)
        }
    }

}