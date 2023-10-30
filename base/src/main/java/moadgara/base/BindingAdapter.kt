package moadgara.base

import android.graphics.drawable.Drawable
import android.text.TextWatcher
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.textfield.TextInputEditText

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

@BindingAdapter(value = ["android:src", "placeholder", "error"], requireAll = false)
fun setImageFromUrl(view: ImageView, url: String?, placeholder: Drawable?, error: Drawable?) {
    Glide.with(view.context)
        .load(url)
        .placeholder(placeholder)
        .error(error)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(view)
}

@BindingAdapter("itemDecoration")
fun setItemDecoration(view: RecyclerView, margin: Float) {
    view.addItemDecoration(HorizontalMarginItemDecoration(margin))
}