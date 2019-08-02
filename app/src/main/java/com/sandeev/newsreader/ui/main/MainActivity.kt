package com.sandeev.newsreader.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.sandeev.newsreader.R
import com.sandeev.newsreader.ui.about.AboutActivity
import com.sandeev.newsreader.ui.main.news.NewsFragment
import com.sandeev.newsreader.ui.main.news.SectionsPagerAdapter
import com.sandeev.newsreader.ui.profile.ProfileActivity
import com.sandeev.newsreader.ui.search.SearchActivity
import com.sandeev.newsreader.util.AppConstants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        setNavigationDrawer()
        setViewPager()
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_search -> {
                startActivity(Intent(this, SearchActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_profile -> {
                startActivity(Intent(this, ProfileActivity::class.java))
            }
            R.id.nav_about -> {
                startActivity(Intent(this, AboutActivity::class.java))
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setNavigationDrawer() {
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
    }

    private fun setViewPager() {
        val fragments = listOf<Fragment>(
            NewsFragment.newInstance(AppConstants.CATEGORY_GENERAL),
            NewsFragment.newInstance(AppConstants.CATEGORY_TECHNOLOGY),
            NewsFragment.newInstance(AppConstants.CATEGORY_ENTERTAINMENT),
            NewsFragment.newInstance(AppConstants.CATEGORY_SPORTS)
        )

        val sectionsPagerAdapter = SectionsPagerAdapter(this, fragments, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)
    }
}
