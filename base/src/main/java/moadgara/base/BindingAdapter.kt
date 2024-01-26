package moadgara.base

import android.graphics.drawable.Drawable
import android.text.Html
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.textfield.TextInputEditText
import moadgara.base.extension.doOnApplyWindowInsets
import moadgara.base.extension.orDefault
import moadgara.base.extension.statusBarPadding
import moadgara.base.extension.toPaddingHolder
import moadgara.base.extension.toPx
import moadgara.base.util.CoilUtil
import moadgara.base.util.HorizontalMarginItemDecoration
import moadgara.base.util.VerticalMarginItemDecoration

@BindingAdapter("registerTextWatcher")
fun setTextWatcher(view: TextInputEditText, watcher: TextWatcher) {
    view.addTextChangedListener(watcher)
}

@BindingAdapter("android:src")
fun setImageResource(view: ImageView, resource: Int) {
    view.setImageResource(resource)
}

@BindingAdapter(value = ["onNavigationItemSelected", "selectNavBarItem"], requireAll = false)
fun setOnNavigationItemSelected(
    view: BottomNavigationView,
    listener: NavigationBarView.OnItemSelectedListener?,
    position: Int?
) {
    if (listener != null) view.setOnItemSelectedListener(listener)
    if (position != null) view.selectedItemId = view.menu.getItem(position).itemId
}

@BindingAdapter(
    value = ["android:src", "placeholder", "error", "tint", "rounded"],
    requireAll = false
)
fun setImageFromUrl(
    view: ImageView,
    url: String?,
    placeholder: Drawable?,
    error: Drawable?,
    tintColor: Int?,
    rounded: Boolean = false
) {
    if (tintColor != null) {
        placeholder?.setTint(ContextCompat.getColor(view.context, tintColor))
        error?.setTint(ContextCompat.getColor(view.context, tintColor))
    }

    view.load(url, CoilUtil.getCachedCoilImageLoader(view.context)) {
        error(error)
        placeholder(placeholder)
        if (rounded) transformations(RoundedCornersTransformation())
    }

}

@BindingAdapter("horizontalItemDecoration")
fun setHorizontalItemDecoration(view: RecyclerView, margin: Float) {
    view.addItemDecoration(HorizontalMarginItemDecoration(margin))
}

@BindingAdapter("verticalItemDecoration", "ignoreFirstTopMargin", requireAll = false)
fun setVerticalItemDecoration(view: RecyclerView, margin: Float, ignoreFirstTopMargin: Boolean = false) {
    view.addItemDecoration(VerticalMarginItemDecoration(margin, ignoreFirstTopMargin))
}

@BindingAdapter(
    "paddingLeftSystemWindowInsets",
    "paddingTopSystemWindowInsets",
    "paddingRightSystemWindowInsets",
    "paddingBottomSystemWindowInsets",
    requireAll = false
)
fun applySystemWindows(
    view: View,
    applyLeft: Boolean,
    applyTop: Boolean,
    applyRight: Boolean,
    applyBottom: Boolean
) {
    view.doOnApplyWindowInsets { _, insets, padding ->
        val insetPadding = insets.toPaddingHolder()

        val insetLeft = if (applyLeft) insetPadding.left else 0
        val insetTop = if (applyTop) insetPadding.top else 0
        val insetRight = if (applyRight) insetPadding.right else 0
        val insetBottom = if (applyBottom) insetPadding.bottom else 0

        view.setPadding(
            padding.left + insetLeft,
            padding.top + insetTop,
            padding.right + insetRight,
            padding.bottom + insetBottom
        )
    }
}

@BindingAdapter("statusBarPadding")
fun setStatusBarPadding(view: View, direction: Int) {
    view.statusBarPadding(direction)
}

@BindingAdapter("toolbarPadding")
fun setToolbarPadding(view: View, padding: Float?) {
    view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
        topMargin = padding?.toInt().orDefault(90.toPx)
    }
}

@BindingAdapter("android:visibility")
fun setVisibility(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("htmlText")
fun setTextFromHtml(view: TextView, text: String) {
    view.text = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
}