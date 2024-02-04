package moadgara.uicomponent.overlay

import android.app.Dialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import moadgara.base.extension.changeVisibility
import moadgara.base.util.ScreenCaptureUtil
import moadgara.uicomponent.R
import moadgara.uicomponent.databinding.LayoutOverlayBaseBinding

class OverlayBaseFragment : DialogFragment(), Overlay {

    companion object {
        private const val KEY_INNER_FRAGMENT_CLASS = "inner-fragment-class"
        private const val KEY_INNER_FRAGMENT_BUNDLE = "inner-fragment-bundle"

        private var instance: OverlayBaseFragment? = null

        fun startOrAdd(
            fragmentManager: FragmentManager, innerFragmentClass: Class<out Fragment>, innerFragmentBundle: Bundle? = null
        ) {
            val bundle = Bundle().apply {
                putString(KEY_INNER_FRAGMENT_CLASS, innerFragmentClass.canonicalName)
                putBundle(KEY_INNER_FRAGMENT_BUNDLE, innerFragmentBundle)
            }

            val fragmentTransaction =
                fragmentManager.beginTransaction().addToBackStack(OverlayBaseFragment::class.java.simpleName)

            if (instance == null) {
                instance = OverlayBaseFragment().apply {
                    arguments = bundle
                    show(fragmentTransaction, OverlayBaseFragment::class.java.simpleName)
                }
            } else {
                instance!!.addInnerFragment(innerFragmentClass, innerFragmentBundle)
            }

        }
    }

    private var innerFragmentClass: String? = null
    private var innerFragmentBundle: Bundle? = null
    private lateinit var fragmentManager: FragmentManager
    private lateinit var binding: LayoutOverlayBaseBinding
    private val toolbarAlphaMap = mutableMapOf<String, Float>()
    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        innerFragmentClass = arguments?.getString(KEY_INNER_FRAGMENT_CLASS)
        innerFragmentBundle = arguments?.getBundle(KEY_INNER_FRAGMENT_BUNDLE)
        fragmentManager = childFragmentManager
    }

    /*
    * By design choice, back press on dialogs cannot be handled by registering
    * activity's onBackPressedDispatcher. Here is the workaround to catch the back press event
    * in dialog fragment.
    * Ref: https://stackoverflow.com/a/66158127
    * Ref-2: https://issuetracker.google.com/issues/149173280
    */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setOnKeyListener { _: DialogInterface, keyCode: Int, keyEvent: KeyEvent ->
                if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.action == KeyEvent.ACTION_UP) {
                    handleBackPress()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O_MR1)
                window?.attributes?.layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = LayoutOverlayBaseBinding.inflate(inflater, container, false)
        ToolbarHelper()
        return binding.root
    }

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (innerFragmentClass != null) {
            val fragmentClass = Class.forName(innerFragmentClass!!) as Class<out Fragment>
            addInnerFragment(fragmentClass, innerFragmentBundle)
        }
    }

    override fun getTheme(): Int {
        return R.style.Theme_FullScreenDialogTheme
    }

    override fun registerScreenCaptureRequest(screenCaptureRequest: ScreenCaptureRequest) {
        dialog?.window?.let {
            ScreenCaptureUtil.captureScreen(window = it) { bitmap ->
                screenCaptureRequest.onScreenCaptureCompleted(bitmap)
            }
        }
    }

    override fun backPress() {
        if (fragmentManager.backStackEntryCount > 1) {
            fragmentManager.popBackStack()
        }
    }

    override fun onToolbarVisibilityChanged(visibility: Float) {
        binding.toolbar.background.alpha = visibility
        currentFragment?.tag?.let { toolbarAlphaMap[it] = visibility }
    }

    private fun handleBackPress() {
        val fragment = fragmentManager.fragments.lastOrNull()
        if (fragment is ToolbarFragment && fragment.onBackPressed()) return
        else {
            if (fragmentManager.backStackEntryCount > 1) {
                fragmentManager.popBackStack()
            } else {
                instance = null
                dismiss()
            }
        }

    }

    private fun addInnerFragment(
        fragmentClass: Class<out Fragment>, fragmentBundle: Bundle? = null
    ) {
        fragmentManager.commit {
            replace(R.id.container, fragmentClass, fragmentBundle, fragmentClass.simpleName)
            addToBackStack(fragmentClass.simpleName)
        }
    }

    private inner class ToolbarHelper {

        init {
            binding.toolbar.background.alpha = 0f

            fragmentManager.addOnBackStackChangedListener {
                val fragment = fragmentManager.fragments.lastOrNull()

                if (fragment == null) {
                    currentFragment = null
                    return@addOnBackStackChangedListener
                }

                if (fragment !is ToolbarFragment) {
                    throw IllegalStateException("Overlay fragment must implement ToolbarFragment!")
                }

                currentFragment = fragment

                val alpha = toolbarAlphaMap[fragment.tag] ?: fragment.initialToolbarAlpha()
                binding.toolbar.background.alpha = alpha
                toolbarAlphaMap[fragment.tag!!] = alpha

                binding.toolbar.toolbarLayout.changeVisibility(fragment.showToolbar())

                binding.toolbar.toolbarTitle.text = fragment.getTitle()

                when (fragment.getToolbarType()) {
                    ToolbarType.TITLE_ONLY -> setButtonVisibilities(showCloseButton = false, showBackButton = false)
                    ToolbarType.BACK -> setButtonVisibilities(showCloseButton = false, showBackButton = true)
                    ToolbarType.CLOSE -> setButtonVisibilities(showCloseButton = true, showBackButton = false)
                    ToolbarType.BACK_CLOSE -> setButtonVisibilities(showCloseButton = true, showBackButton = true)
                    ToolbarType.BACK_AUTO_CLOSE -> setButtonVisibilities(
                        showCloseButton = fragmentManager.backStackEntryCount > 1,
                        showBackButton = true
                    )
                }
            }

            binding.toolbar.back.setOnClickListener {
                handleBackPress()
            }

            binding.toolbar.close.setOnClickListener {
                instance = null
                dialog?.dismiss()
            }
        }
    }

    private fun setButtonVisibilities(showCloseButton: Boolean, showBackButton: Boolean) {
        binding.toolbar.run {
            close.changeVisibility(showCloseButton)
            back.changeVisibility(showBackButton)
        }
    }
}

enum class ToolbarType {
    TITLE_ONLY,
    CLOSE,
    BACK,
    BACK_AUTO_CLOSE,
    BACK_CLOSE
}