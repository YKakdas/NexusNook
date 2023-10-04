package moadgara.base

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel

inline fun <reified VM : ViewModel> ComponentActivity.viewModel(noinline creator: () -> VM) =
    viewModels<VM> { ViewModelFactory(creator) }