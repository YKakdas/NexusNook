package moadgara.base

import android.text.TextWatcher
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.textfield.TextInputEditText

@BindingAdapter("registerTextWatcher")
fun setNavigationMenuListener(view: TextInputEditText, watcher: TextWatcher) {
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