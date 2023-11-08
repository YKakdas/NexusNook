package moadgara.main.overlay

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import moadgara.base.extension.applyFullScreen
import moadgara.main.R
import moadgara.main.databinding.LayoutOverlayBaseBinding

class OverlayBaseFragment : DialogFragment() {

    companion object {
        private const val KEY_INNER_FRAGMENT_CLASS = "inner-fragment-class"
        private const val KEY_INNER_FRAGMENT_BUNDLE = "inner-fragment-bundle"
        private var instance: OverlayBaseFragment? = null

        fun startOrAdd(
            fragmentManager: FragmentManager,
            innerFragmentClass: Class<out Fragment>,
            innerFragmentBundle: Bundle? = null
        ) {
            val bundle = Bundle().apply {
                putString(KEY_INNER_FRAGMENT_CLASS, innerFragmentClass.canonicalName)
                putBundle(KEY_INNER_FRAGMENT_BUNDLE, innerFragmentBundle)
            }

            val fragmentTransaction = fragmentManager.beginTransaction()
                .addToBackStack(OverlayBaseFragment::class.java.simpleName)

            if (instance == null) {
                instance = OverlayBaseFragment().apply {
                    arguments = bundle
                    show(fragmentTransaction, OverlayBaseFragment::class.java.simpleName)
                }
            } else {
                instance!!.addInnerFragment(innerFragmentClass, bundle)
            }

        }
    }

    private var innerFragmentClass: String? = null
    private var innerFragmentBundle: Bundle? = null
    private lateinit var fragmentManager: FragmentManager
    private lateinit var binding: LayoutOverlayBaseBinding

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
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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
        return moadgara.uicomponent.R.style.Theme_FullScreenDialogTheme
    }

    private fun handleBackPress() {
        if (fragmentManager.backStackEntryCount > 1) {
            fragmentManager.popBackStack()
        } else {
            instance = null
            dismiss()
        }
    }

    private fun addInnerFragment(
        fragmentClass: Class<out Fragment>,
        fragmentBundle: Bundle? = null
    ) {
        fragmentManager.commit {
            replace(
                R.id.container,
                fragmentClass,
                fragmentBundle,
                fragmentClass.simpleName
            )
            addToBackStack(fragmentClass.simpleName)
        }
    }

    private inner class ToolbarHelper {
        init {
            fragmentManager.addOnBackStackChangedListener {
                val fragment =
                    fragmentManager.fragments.lastOrNull()
                        ?: return@addOnBackStackChangedListener

                if (fragment is ToolbarTitle) {
                    binding.toolbar.toolbarTitle.text = fragment.getTitle()
                }

                if (fragmentManager.backStackEntryCount > 1) {
                    binding.toolbar.close.visibility = View.VISIBLE
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
}