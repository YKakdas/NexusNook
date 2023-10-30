package moadgara.main.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import moadgara.main.R
import moadgara.main.databinding.FragmentDiscoverBinding
import moadgara.uicomponent.AlertDialog
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

        val myAdapter = genericAdapter<PreviewListItemData> {}

        binding.trendingGamesPreviewList.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            setItemViewCacheSize(40)
            adapter = myAdapter
        }

        viewModel.getTrendingGamesPreviewList().observe(viewLifecycleOwner) {
            binding.trendingGamesPreviewListShimmer.visibility = View.GONE
            binding.trendingGamesPreviewList.root.visibility = View.VISIBLE

            myAdapter.submitList(it)
        }

    }

}