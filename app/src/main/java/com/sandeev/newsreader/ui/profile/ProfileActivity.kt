package com.sandeev.newsreader.ui.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.AppBarLayout
import com.sandeev.newsreader.R
import kotlinx.android.synthetic.main.activity_profile.*
import kotlin.math.abs

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setAppBarScrollListener()
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
}
