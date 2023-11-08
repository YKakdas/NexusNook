package moadgara.base.extension

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import moadgara.base.ViewModelFactory

inline fun <reified VM : ViewModel> ComponentActivity.viewModel(noinline creator: () -> VM) =
    viewModels<VM> { ViewModelFactory(creator) }