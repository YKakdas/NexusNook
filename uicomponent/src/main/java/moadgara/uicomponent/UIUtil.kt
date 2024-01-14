package moadgara.uicomponent

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import moadgara.uicomponent.overlay.OverlayBaseFragment
import moadgara.uicomponent.webview.WebViewFragment
import moadgara.uicomponent.webview.WebViewFragment.Companion.KEY_WEB_URL

object UIUtil {
    fun showWebView(fragmentManager: FragmentManager, url: String?) {
        val bundle = Bundle()
        bundle.putString(KEY_WEB_URL, url)
        OverlayBaseFragment.startOrAdd(fragmentManager, WebViewFragment::class.java, bundle)
    }
}