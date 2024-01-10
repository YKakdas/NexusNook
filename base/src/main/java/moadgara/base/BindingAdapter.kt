package moadgara.base

import android.graphics.drawable.Drawable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.textfield.TextInputEditText
import moadgara.base.extension.doOnApplyWindowInsets
import moadgara.base.extension.statusBarPadding
import moadgara.base.extension.toPaddingHolder
import moadgara.base.util.CoilUtil
import moadgara.base.util.HorizontalMarginItemDecoration

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
    value = ["android:src", "placeholder", "error", "tint"],
    requireAll = false
)
fun setImageFromUrl(
    view: ImageView,
    url: String?,
    placeholder: Drawable?,
    error: Drawable?,
    tintColor: Int?
) {
    if (tintColor != null) {
        placeholder?.setTint(ContextCompat.getColor(view.context, tintColor))
        error?.setTint(ContextCompat.getColor(view.context, tintColor))
    }

    view.load(url, CoilUtil.getCachedCoilImageLoader(view.context)) {
        error(error)
        transformations(RoundedCornersTransformation())
    }

}

@BindingAdapter("itemDecoration")
fun setItemDecoration(view: RecyclerView, margin: Float) {
    view.addItemDecoration(HorizontalMarginItemDecoration(margin))
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