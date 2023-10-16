package moadgara.uicomponent

import android.os.Bundle
import android.widget.Toast
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
    private lateinit var dialogNoParam: AlertDialog.Builder
    private lateinit var dialogNeutral: AlertDialog.Builder
    private lateinit var dialogPositiveNegative: AlertDialog.Builder
    private lateinit var dialogWithTitle: AlertDialog.Builder
    private lateinit var dialogWithDescription: AlertDialog.Builder
    private lateinit var dialogWarning: AlertDialog.Builder
    private lateinit var dialogError: AlertDialog.Builder
    private lateinit var dialogSuccess: AlertDialog.Builder
    private lateinit var dialogQuestion: AlertDialog.Builder
    private lateinit var dialogAnnouncement: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<WidgetsPlaygroundBinding>(
            this,
            R.layout.widgets_playground
        )
        
        initTestDialogs()

        binding.lifecycleOwner = this
        binding.vm = viewModel
    }

    private fun initTestDialogs() {
        dialogNoParam = AlertDialog.Builder(this)

        dialogNeutral = AlertDialog.Builder(this)
            .setNeutralText("SOMETHING")
            .setNeutralListener {
                Toast.makeText(
                    this,
                    "Neutral listener has been called successfully!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        dialogPositiveNegative = prepareDialogYesNoTemplate()
        dialogWithTitle =
            prepareDialogYesNoTemplate()
                .setTitle("This is the title without any description")
        dialogWithDescription =
            prepareDialogYesNoTemplate()
                .setDescription("This is the description without any title")

        dialogWarning =
            prepareDialogWithTitleAndDescription().setType(AlertDialog.Type.WARNING)
        dialogError =
            prepareDialogWithTitleAndDescription().setType(AlertDialog.Type.ERROR)
        dialogSuccess =
            prepareDialogWithTitleAndDescription().setType(AlertDialog.Type.SUCCESS)
        dialogQuestion =
            prepareDialogWithTitleAndDescription().setType(AlertDialog.Type.QUESTION)
        dialogAnnouncement =
            prepareDialogWithTitleAndDescription().setType(AlertDialog.Type.ANNOUNCEMENT)
    }

    private fun prepareDialogWithTitleAndDescription(): AlertDialog.Builder {
        return prepareDialogYesNoTemplate()
            .setTitle("This is the title")
            .setDescription(
                "This is the description text. I am writing more nonsense sentences to " +
                        "see how the ui react with long text. I hope this length is " +
                        "enough for testing. If not, I can add more and more nonsense into this" +
                        "description text. But, hopefully, that would be enough. And, I should not" +
                        "forget to remove this test ui in the prod, otherwise I  might look like" +
                        "I am talking all by myself, which is totally not accurate."
            )
    }

    private fun prepareDialogYesNoTemplate(): AlertDialog.Builder {
        val dialogYesNoTemplate = AlertDialog.Builder(this)
            .setPositiveText("YES")
            .setNegativeText("NO")
            .setPositiveListener {
                Toast.makeText(
                    this,
                    "positive listener has been called successfully!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .setNegativeListener {
                Toast.makeText(
                    this,
                    "negative listener has been called successfully!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        return dialogYesNoTemplate
    }

    private fun initViewModel(): UIComponentTestViewModel {
        return UIComponentTestViewModel(
            dialogNoParam,
            dialogNeutral,
            dialogPositiveNegative,
            dialogWithTitle,
            dialogWithDescription,
            dialogWarning,
            dialogError,
            dialogSuccess,
            dialogQuestion,
            dialogAnnouncement,
        )
    }
}