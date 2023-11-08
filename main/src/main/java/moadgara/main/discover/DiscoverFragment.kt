package moadgara.main.discover

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Space
import androidx.annotation.DimenRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import moadgara.main.R
import moadgara.main.databinding.FragmentDiscoverBinding
import moadgara.main.databinding.LayoutPreviewListBinding
import moadgara.main.databinding.LayoutPreviewListShimmerBinding
import moadgara.uicomponent.AlertDialog
import moadgara.uicomponent.adapter.GenericAdapter
import moadgara.uicomponent.adapter.genericAdapter
import moadgara.uicomponent.alertDialog
import org.koin.android.ext.android.inject

class DiscoverFragment : Fragment(R.layout.fragment_discover) {

    private val viewModel: DiscoverViewModel by inject()

    private lateinit var allPreviewListLiveData: List<LiveData<List<PreviewListItemData>?>>
    private lateinit var binding: FragmentDiscoverBinding
    private var initialized: Boolean = false

    companion object {
        fun newInstance(): DiscoverFragment {
            return DiscoverFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
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

        allPreviewListLiveData = viewModel.getAllPreviewListLiveData()

        val pageMetaData = viewModel.preparePageMetaData()
        allPreviewListLiveData.forEachIndexed { index, liveData ->
            populateView(
                index, pageMetaData[index], liveData
            )
        }
        if (!initialized) {
            viewModel.fetchData()
            initialized = true
        }
    }

    private fun populateView(
        index: Int, metaData: PreviewListMetaData, liveData: LiveData<List<PreviewListItemData>?>
    ) {
        val actualViewsParent = binding.inflateViewRoot
        val childActualViewBinding =
            inflatePreviewListLayout<LayoutPreviewListBinding>(actualViewsParent)
        val actualViewSpace =
            createSpaceView(moadgara.uicomponent.R.dimen.margin_high, actualViewsParent.context)

        val shimmerViewsParent = binding.shimmerInflateViewRoot
        val childShimmerViewBinding =
            inflatePreviewListLayout<LayoutPreviewListShimmerBinding>(shimmerViewsParent)
        val shimmerViewSpace =
            createSpaceView(
                moadgara.uicomponent.R.dimen.shimmer_preview_list_space_between_items,
                shimmerViewsParent.context
            )

        childActualViewBinding.data = metaData
        childActualViewBinding.root.visibility = View.GONE
        actualViewSpace.visibility = View.GONE

        actualViewsParent.addView(childActualViewBinding.root, index * 2)
        actualViewsParent.addView(actualViewSpace, index * 2 + 1)
        shimmerViewsParent.addView(childShimmerViewBinding.root, index * 2)
        shimmerViewsParent.addView(shimmerViewSpace)

        setupRecyclerViewAndObserve(
            childActualViewBinding.recyclerView,
            genericAdapter {},
            liveData,
            ViewReferencePass(
                childActualViewBinding.root,
                actualViewSpace,
                childShimmerViewBinding.root,
                shimmerViewSpace
            )
        )

    }

    private inline fun <reified T : ViewDataBinding> inflatePreviewListLayout(parent: ViewGroup): T {
        val layout = if (T::class == LayoutPreviewListBinding::class) {
            R.layout.layout_preview_list
        } else {
            R.layout.layout_preview_list_shimmer
        }
        return DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)
    }

    private fun createSpaceView(@DimenRes height: Int, context: Context): Space {
        val space = Space(context)
        space.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, resources.getDimension(height).toInt()
        )
        return space
    }

    private fun setupRecyclerViewAndObserve(
        recyclerView: RecyclerView,
        adapter: GenericAdapter<PreviewListItemData>,
        listLiveData: LiveData<List<PreviewListItemData>?>,
        viewReferencePass: ViewReferencePass,
    ) {
        recyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            setItemViewCacheSize(40)
            this.adapter = adapter
        }

        listLiveData.observe(viewLifecycleOwner) {
            viewReferencePass.shimmerView.visibility = View.GONE
            viewReferencePass.shimmerSpace.visibility = View.GONE
            if (it != null) {
                viewReferencePass.actualView.visibility = View.VISIBLE
                viewReferencePass.actualSpace.visibility = View.VISIBLE
                adapter.submitList(it)
            }
        }
    }

}

private data class ViewReferencePass(
    val actualView: View,
    val actualSpace: Space,
    val shimmerView: View,
    val shimmerSpace: Space
)