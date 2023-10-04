package moadgara.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner

inline fun <reified VM : ViewModel> Fragment.viewModel(noinline creator: () -> VM) =
    createViewModelLazy(
        viewModelClass = VM::class,
        storeProducer = { viewModelStore },
        factoryProducer = { ViewModelFactory(creator) })

inline fun <reified VM : ViewModel> Fragment.viewModel(
    noinline owner: () -> ViewModelStoreOwner = { this },
    noinline creator: () -> VM
) =
    createViewModelLazy(
        viewModelClass = VM::class,
        storeProducer = { owner().viewModelStore },
        factoryProducer = { ViewModelFactory(creator) })

inline fun <reified VM : ViewModel> Fragment.activityViewModel(noinline creator: () -> VM) =
    createViewModelLazy(
        viewModelClass = VM::class,
        storeProducer = { requireActivity().viewModelStore },
        factoryProducer = { ViewModelFactory(creator) })