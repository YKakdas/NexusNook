package moadgara.base

import android.text.TextWatcher
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText

@BindingAdapter("registerTextWatcher")
fun setNavigationMenuListener(view: TextInputEditText, watcher: TextWatcher) {
    view.addTextChangedListener(watcher)
}

@BindingAdapter("android:src")
fun setImageResource(view: ImageView, resource: Int) {
    view.setImageResource(resource)
}
