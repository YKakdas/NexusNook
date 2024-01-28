package moadgara.uicomponent.webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebViewClient
import moadgara.base.BaseFragment
import moadgara.base.viewBinding
import moadgara.uicomponent.R
import moadgara.uicomponent.databinding.LayoutWebviewBinding
import moadgara.uicomponent.overlay.ToolbarFragment
import moadgara.uicomponent.overlay.ToolbarType


class WebViewFragment : BaseFragment(R.layout.layout_webview), ToolbarFragment {
    private var webUrl: String? = null
    private var title: String = ""
    private val binding by viewBinding(LayoutWebviewBinding::bind)

    companion object {
        const val KEY_WEB_URL = "web_url"
        const val KEY_TITLE = "title"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webUrl = arguments?.getString(KEY_WEB_URL)
        title = arguments?.getString(KEY_TITLE).orEmpty()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWebSettings()
        binding.webview.run {
            webViewClient = WebViewClient()
            loadUrl(webUrl ?: throw IllegalArgumentException("Web URL cannot be null!"))
        }
    }

    override fun getTitle(): String = title

    override fun showToolbar(): Boolean = true

    override fun onBackPressed(): Boolean {
        return if (binding.webview.canGoBack()) {
            binding.webview.goBack()
            true
        } else {
            false
        }
    }

    override fun getToolbarType(): ToolbarType = ToolbarType.BACK_CLOSE

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebSettings() {
        val settings: WebSettings = this.binding.webview.settings
        settings.javaScriptEnabled = true
        settings.allowContentAccess = true
        settings.domStorageEnabled = true
    }

}