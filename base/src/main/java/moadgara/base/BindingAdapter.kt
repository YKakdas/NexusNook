package moadgara.base

import android.text.TextWatcher
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText

@BindingAdapter("registerTextWatcher")
fun setNavigationMenuListener(view: TextInputEditText, watcher: TextWatcher) {
    view.addTextChangedListener(watcher)
}