package moadgara.main

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import moadgara.main.databinding.FragmentMainBinding
import moadgara.uicomponent.R as uiComponentR

class MainFragment : Fragment(R.layout.fragment_main) {

    private var isFabOpen = false
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupFabListeners()
    }

    private fun setupFabListeners() {
        binding.fab.setOnClickListener {
            val angle = if (isFabOpen) -180f else 180f
            isFabOpen = !isFabOpen

            val tintColor =
                if (isFabOpen) uiComponentR.color.colorPrimary else uiComponentR.color.fountain_blue

            it.animate().rotationBy(angle).duration = 500L
            ImageViewCompat.setImageTintList(
                binding.fab,
                ColorStateList.valueOf(
                    resources.getColor(
                        tintColor,
                        context?.theme
                    )
                )
            )
        }
    }
}