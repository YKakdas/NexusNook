package moadgara.uicomponent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import moadgara.base.viewModel
import moadgara.uicomponent.databinding.WidgetsPlaygroundBinding

/**
 * Showcase for designed ui components with custom themes, styles, and colors. Should only be used
 * during development. Does not need to be in the production.
 */
class UIComponentTestActivity : AppCompatActivity() {

    private val viewModel by viewModel(::initViewModel)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<WidgetsPlaygroundBinding>(
            this,
            R.layout.widgets_playground
        )

        binding.lifecycleOwner = this
        binding.vm = viewModel
    }


    private fun initViewModel(): UIComponentTestViewModel {
        return UIComponentTestViewModel()
    }
}