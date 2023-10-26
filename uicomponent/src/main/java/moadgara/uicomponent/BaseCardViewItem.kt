package moadgara.uicomponent

import androidx.annotation.LayoutRes

open class BaseCardViewItem(
    @LayoutRes val layoutId: Int,
    val data: Any? = null,
    val action: ((Any?) -> (Unit))? = null
)