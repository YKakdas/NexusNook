package moadgara.uicomponent.overlay

interface ToolbarFragment {
    fun getTitle(): String?

    fun showToolbar(): Boolean

    fun onBackPressed(): Boolean

    fun getToolbarType(): ToolbarType
}
