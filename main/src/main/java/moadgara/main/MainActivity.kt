package moadgara.main

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setActionBarColor()

        supportFragmentManager.beginTransaction().replace(
            android.R.id.content,
            MainFragment.newInstance(),
            MainFragment::class.java.simpleName
        ).commit()

    }

    private fun setActionBarColor() {
        val actionBar = supportActionBar

        actionBar?.elevation = 0f

        val typedValue = TypedValue()
        theme.resolveAttribute(android.R.attr.windowBackground, typedValue, true)
        val color = typedValue.data

        actionBar?.setBackgroundDrawable(ColorDrawable(color))
    }
}

