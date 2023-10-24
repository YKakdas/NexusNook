package moadgara.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import moadgara.main.databinding.FragmentMainBinding
import org.koin.android.ext.android.inject

class MainFragment : Fragment(R.layout.fragment_main) {

    private val navigator: MainNavigator by inject()
    private val viewModel: MainViewModel by inject()

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
        binding.vm = viewModel
    }

}