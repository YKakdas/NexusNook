package moadgara.main.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import moadgara.base.extension.launchInIO
import moadgara.base.extension.observeOnce
import moadgara.base.util.CoilUtil
import moadgara.main.R
import moadgara.main.databinding.FragmentDiscoverBinding
import moadgara.uicomponent.AlertDialog
import moadgara.uicomponent.ProgressDialog
import moadgara.uicomponent.alertDialog
import org.koin.android.ext.android.inject

class DiscoverFragment : Fragment(R.layout.fragment_discover) {

    private val viewModel: DiscoverViewModel by inject()
    private var progressDialog: ProgressDialog = ProgressDialog.newInstance()

    private lateinit var binding: FragmentDiscoverBinding
    private lateinit var discoverHelper: DiscoverViewHelper

    companion object {
        fun newInstance(): DiscoverFragment {
            return DiscoverFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
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
                description(it ?: resources.getString(R.string.generic_error_description))
                neutralText(R.string.alert_dialog_neutral_button_text)
                type(AlertDialog.Type.ERROR)
            }
        }
    }

    private fun observeImages() {
        viewModel.getImages().observeOnce(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                launchInIO {
                    CoilUtil.prefetchImages(requireContext(), it) {
                        progressDialog.dismissProgress()
                    }
                }
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