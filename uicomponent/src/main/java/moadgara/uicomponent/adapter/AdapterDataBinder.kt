package moadgara.uicomponent.adapter

import androidx.databinding.ViewDataBinding

internal class AdapterDataBinder(val id: Int) {
    fun bind(binding: ViewDataBinding, data: Any) {
        binding.setVariable(id, data)
    }
}