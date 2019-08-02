package com.sandeev.newsreader.ui.about

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.AppBarLayout
import com.sandeev.newsreader.BuildConfig
import com.sandeev.newsreader.R
import kotlinx.android.synthetic.main.activity_about.*
import kotlin.math.abs

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setSupportActionBar(toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setAppBarScrollListener()

        inflateData()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setAppBarScrollListener() {
        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, i ->
            if (abs(i) - appBarLayout.totalScrollRange == 0) {
                toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.black))
            } else {
                toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.transparent))
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun inflateData() {
        tv_version.text = "Version ${BuildConfig.VERSION_NAME}"
    }
}
