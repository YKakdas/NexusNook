package moadgara.uicomponent.custom_view

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import moadgara.base.extension.loadGif
import moadgara.uicomponent.R
import moadgara.uicomponent.databinding.LayoutProgressBinding

class ProgressDialog : DialogFragment() {
    private lateinit var binding: LayoutProgressBinding

    companion object {
        private var shown = false

        fun newInstance(): ProgressDialog {
            shown = false
            return ProgressDialog()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.setCanceledOnTouchOutside(false)
        isCancelable = false
    }

    override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = LayoutProgressBinding.inflate(inflater, container, false)
        dialog?.window?.setDimAmount(0.85f)
        showLoadingAnimation()
        return binding.root
    }

    override fun show(manager: FragmentManager, tag: String?) {
        super.show(manager, tag)
        shown = true
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        shown = false
    }

    fun showProgress(show: Boolean, fragmentManager: FragmentManager) {
        if (show && !shown) {
            show(fragmentManager, ProgressDialog::class.java.simpleName)
        } else if (!show && shown) {
            dismissAllowingStateLoss()
        }
    }

    fun dismissProgress() = dismissAllowingStateLoss()


    private fun showLoadingAnimation() {
        binding.progressImage.loadGif(
            R.raw.loading_animation,
          listOf(R.anim.progress_rotate)
        )
    }
}