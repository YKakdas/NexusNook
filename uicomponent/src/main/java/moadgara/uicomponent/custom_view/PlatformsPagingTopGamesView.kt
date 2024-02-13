package moadgara.uicomponent.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import moadgara.uicomponent.BR
import moadgara.uicomponent.databinding.PlatformsPagingTopGamesItemLayoutBinding

@BindingMethods(
    value = [BindingMethod(
        type = PlatformsPagingTopGamesView::class, attribute = "setData", method = "setData"
    )]
)
class PlatformsPagingTopGamesView(context: Context, attributeSet: AttributeSet?, defStyleInt: Int) :
    LinearLayout(context, attributeSet, defStyleInt) {
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context) : this(context, null, 0)

    private lateinit var data: List<Pair<String, Int>>

    init {
        orientation = VERTICAL
    }

    fun setData(data: List<Pair<String, Int>>) {
        this.data = data
        removeAllViews()
        initializeView()
    }

    private fun initializeView() {
        data = data.takeIf { it.size > 3 }?.subList(0, 3).orEmpty()
        data.forEach {
            val binding = PlatformsPagingTopGamesItemLayoutBinding.inflate(LayoutInflater.from(context), this, true)
            binding.setVariable(BR.name, it.first)
            binding.setVariable(BR.addedCount, it.second)
            binding.executePendingBindings()
        }
    }

}