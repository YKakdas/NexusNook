package moadgara.base.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun LifecycleCoroutineScope.launchInIO(call: () -> Unit) {
    launch {
        withContext(Dispatchers.IO) { call.invoke() }
    }
}

fun Fragment.launchInIO(call: () -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        withContext(Dispatchers.IO) { call.invoke() }
    }
}