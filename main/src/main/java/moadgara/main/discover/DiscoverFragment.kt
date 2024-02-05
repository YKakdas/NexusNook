package moadgara.main.discover

import android.os.Bundle
import android.view.View
import moadgara.uicomponent.BaseFragment
import moadgara.base.extension.launchInIO
import moadgara.base.extension.observeOnce
import moadgara.base.extension.orDefault
import moadgara.base.util.CoilUtil
import moadgara.base.viewBinding
import moadgara.main.R
import moadgara.main.databinding.FragmentDiscoverBinding
import moadgara.uicomponent.AlertDialog
import moadgara.uicomponent.custom_view.ProgressDialog
import moadgara.uicomponent.alertDialog
import org.koin.android.ext.android.inject

class DiscoverFragment : BaseFragment(R.layout.fragment_discover) {

    private val viewModel: DiscoverViewModel by inject()
    private var progressDialog: ProgressDialog = ProgressDialog.newInstance()

    private val binding by viewBinding(FragmentDiscoverBinding::bind)
    private lateinit var discoverHelper: DiscoverViewHelper

    companion object {
        fun newInstance(): DiscoverFragment {
            return DiscoverFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        progressDialog.showProgress(true, parentFragmentManager)
        observeEvents()
        setupHelper()
        initialize()
    }

    private fun observeEvents() {
        observeError()
        observeImages()
    }

    private fun observeError() {
        viewModel.getMessage().observe(viewLifecycleOwner) {
            alertDialog(requireContext()) {
                title(R.string.generic_error_title)
                description(it.orDefault(resources.getString(R.string.generic_error_description)))
                neutralText(R.string.alert_dialog_neutral_button_text)
                type(AlertDialog.Type.ERROR)
            }
        }
    }

    private fun observeImages() {
        viewModel.getImages().observeOnce(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                launchInIO {
                    CoilUtil.prefetchImages(requireContext(), it) {
                        progressDialog.dismissProgress()
                    }
                }
            } else {
                progressDialog.dismissProgress()
            }
        }
    }

    private fun setupHelper() {
        val observables = viewModel.getAllPreviewListsLiveData()
        val pageMetaData = viewModel.preparePageMetaData()

        DiscoverViewHelper(this).also { discoverHelper = it }
            .setBinding(binding)
            .setDataObservers(observables)
            .setListMetaData(pageMetaData)
            .inflateViews()
    }

    private fun initialize() {
        viewModel.fetchData()
    }

}