package moadgara.main

import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import moadgara.main.databinding.FragmentMainBinding
import org.koin.android.ext.android.inject
import moadgara.uicomponent.R as uiComponentR

class MainFragment : Fragment(R.layout.fragment_main) {
    private val viewModel: MainViewModel by inject()

    private lateinit var binding: FragmentMainBinding

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.vm = viewModel

        /* For now, it fixes extra padding issue on fullscreen, investigate the issue later
         * https://stackoverflow.com/questions/50207843/bottomnavigationview-jumps-up-leaving-blank-space-when-fullscreen/67394348#67394348
         */
        binding.bottomNavView.setOnApplyWindowInsetsListener(null)

        setupFabClickListener(binding.fab)
    }

    private fun setupFabClickListener(fab: FloatingActionButton) {
        viewModel.getIsFabClicked().observe(viewLifecycleOwner) {
            val angle = if (it) 180f else -180f
            val tintColor =
                if (it) uiComponentR.color.colorPrimary else uiComponentR.color.fountain_blue

            fab.animate().rotationBy(angle).duration = 500L
            ImageViewCompat.setImageTintList(
                fab as ImageView,
                ColorStateList.valueOf(ContextCompat.getColor(requireContext(), tintColor))
            )
        }
    }

}
