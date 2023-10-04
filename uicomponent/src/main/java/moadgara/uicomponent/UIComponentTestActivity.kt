package moadgara.uicomponent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Showcase for designed ui components with custom themes, styles, and colors. Should only be used
 * during development. Does not need to be in the production.
 */
class UIComponentTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.widgets_playground)
    }
}