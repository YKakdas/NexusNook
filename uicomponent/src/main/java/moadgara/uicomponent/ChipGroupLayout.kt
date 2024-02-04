package moadgara.uicomponent

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class ChipGroupLayout(context: Context, attributeSet: AttributeSet?, defStyleInt: Int) :
    ChipGroup(context, attributeSet, defStyleInt) {

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context) : this(context, null, 0)

    fun initializeChips(chipList: List<String>) {
        chipList.forEach {
            val chip = Chip(context)
            chip.text = it
            chip.setTextColor(ContextCompat.getColor(context, R.color.white_smoke))
            chip.chipBackgroundColor = ContextCompat.getColorStateList(context, R.color.thunder)
            addView(chip)
        }
    }

}

@BindingAdapter("setChips")
fun initializeChips(view: ChipGroupLayout, chipList: List<String>) {
    view.initializeChips(chipList)
}