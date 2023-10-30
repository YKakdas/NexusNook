package moadgara.main.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import moadgara.main.R
import moadgara.main.databinding.FragmentDiscoverBinding
import moadgara.uicomponent.AlertDialog
import moadgara.uicomponent.adapter.GenericAdapter
import moadgara.uicomponent.adapter.genericAdapter
import moadgara.uicomponent.alertDialog
import org.koin.android.ext.android.inject

class DiscoverFragment : Fragment(R.layout.fragment_discover) {

    private val viewModel: DiscoverViewModel by inject()

    private lateinit var binding: FragmentDiscoverBinding

    companion object {
        fun newInstance(): DiscoverFragment {
            return DiscoverFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        binding.data = viewModel.preparePageMetaData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getMessage().observe(viewLifecycleOwner) {
            alertDialog(requireContext()) {
                title(R.string.generic_error_title)
                description(it ?: resources.getString(R.string.generic_error_description))
                neutralText(R.string.alert_dialog_neutral_button_text)
                type(AlertDialog.Type.ERROR)
            }
        }

        setupTrendingGamesView()
        setupBestOfTheYearGamesView()

    }

    private fun setupTrendingGamesView() {
        setupRecyclerViewAndObserve(
            binding.trendingGamesPreviewList.recyclerView,
            genericAdapter {},
            viewModel.getTrendingGamesPreviewList(),
            binding.trendingGamesPreviewList.root,
            binding.trendingGamesPreviewListShimmer
        )
    }

    private fun setupBestOfTheYearGamesView() {
        setupRecyclerViewAndObserve(
            binding.bestOfTheYearGamesPreviewList.recyclerView,
            genericAdapter {},
            viewModel.getBestOfTheYearGamesPreviewList(),
            binding.bestOfTheYearGamesPreviewList.root,
            binding.bestOfTheYearGamesPreviewListShimmer
        )
    }

    private fun setupRecyclerViewAndObserve(
        recyclerView: RecyclerView,
        adapter: GenericAdapter<PreviewListItemData>,
        listLiveData: LiveData<List<PreviewListItemData>>,
        actualView: View,
        shimmerView: View
    ) {
        recyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            setItemViewCacheSize(40)
            this.adapter = adapter
        }

        listLiveData.observe(viewLifecycleOwner) {
            shimmerView.visibility = View.GONE
            actualView.visibility = View.VISIBLE

            adapter.submitList(it)
        }
    }

}