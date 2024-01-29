package moadgara.uicomponent.overlay

interface ToolbarFragment {
    fun getTitle(): String?

    fun initialToolbarAlpha(): Float

    fun onBackPressed(): Boolean

    fun getToolbarType(): ToolbarType
}
