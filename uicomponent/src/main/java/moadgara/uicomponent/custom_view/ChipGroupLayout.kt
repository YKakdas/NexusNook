package moadgara.uicomponent.custom_view

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import moadgara.uicomponent.R

@BindingMethods(
    value = [BindingMethod(
        type = ChipGroupLayout::class, attribute = "setChips", method = "initializeChips"
    ), BindingMethod(
        type = ChipGroupLayout::class, attribute = "chipClickListener", method = "setOnChipClicked"
    )]
)
class ChipGroupLayout(context: Context, attributeSet: AttributeSet?, defStyleInt: Int) :
    ChipGroup(context, attributeSet, defStyleInt) {

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context) : this(context, null, 0)

    private var onChipClicked: ((Int?, String?) -> Unit)? = null

    fun setOnChipClicked(onChipClicked: ((Int?, String?) -> Unit)?) {
        this.onChipClicked = onChipClicked
    }

    fun initializeChips(chipList: List<Pair<Int?, String?>>) {
        chipList.filter { it.first != null && it.second != null }.forEach { chipData ->
            val chip = Chip(context)
            chip.text = chipData.second
            chip.setTextColor(ContextCompat.getColor(context, R.color.white_smoke))
            chip.chipBackgroundColor = ContextCompat.getColorStateList(context, R.color.thunder)
            chip.setPadding(0, 0, 0, 0)
            chip.setEnsureMinTouchTargetSize(true)
            chip.setOnClickListener { onChipClicked?.invoke(chipData.first, chipData.second) }
            addView(chip)
        }
    }

}