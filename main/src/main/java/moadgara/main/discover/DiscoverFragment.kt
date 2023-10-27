package moadgara.main.discover

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import moadgara.main.R
import moadgara.uicomponent.AlertDialog
import moadgara.uicomponent.ProgressDialog
import moadgara.uicomponent.alertDialog
import org.koin.android.ext.android.inject

class DiscoverFragment : Fragment(R.layout.fragment_discover) {

    private val viewModel: DiscoverViewModel by inject()
    private lateinit var progressDialog: ProgressDialog

    companion object {
        fun newInstance(): DiscoverFragment {
            return DiscoverFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        progressDialog = ProgressDialog.newInstance()

        viewModel.getProgress().observe(viewLifecycleOwner) {
            progressDialog.showProgress(it, childFragmentManager)
        }

        viewModel.getMessage().observe(viewLifecycleOwner) {
            alertDialog(requireContext()) {
                title(R.string.generic_error_title)
                description(it ?: resources.getString(R.string.generic_error_description))
                neutralText(R.string.alert_dialog_neutral_button_text)
                type(AlertDialog.Type.ERROR)
            }
        }

    }

}