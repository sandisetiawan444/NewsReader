package com.sandeev.newsreader.ui.search

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sandeev.newsreader.R
import com.sandeev.newsreader.ui.base.BaseActivity
import com.sandeev.newsreader.ui.main.news.NewsAdapter
import com.sandeev.newsreader.ui.main.news.detail.NewsDetailActivity
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchActivity : BaseActivity() {

    private lateinit var viewModel: SearchViewModel
    private lateinit var mainAdapter: NewsAdapter

    private var queryTextChangeJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setSearchView()
        setRecyclerView()
        setViewModel()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setSearchView() {
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                launch { searchData(newText) }
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                search_view.clearFocus()
                return true
            }
        }

        search_view.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setOnQueryTextListener(queryTextListener)
        }
    }

    private suspend fun searchData(query: String) {
        queryTextChangeJob?.cancel()
        queryTextChangeJob = launch(Dispatchers.Main) {
            delay(500)

            if (query.isEmpty()) return@launch

            mainAdapter.setArticles(ArrayList())
            viewModel.searchArticles(query)
        }
    }

    private fun setRecyclerView() {
        mainAdapter = NewsAdapter().apply {
            setHeadlineEnable(false)
            onItemClick = {
                startActivity(
                    Intent(this@SearchActivity, NewsDetailActivity::class.java)
                        .putExtra(NewsDetailActivity.EXTRA_ARTICLE, it)
                )
            }
        }

        rv_news.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mainAdapter
        }
    }

    private fun setViewModel() {
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java).apply {
            articles.observe(this@SearchActivity, Observer {
                mainAdapter.setArticles(it)
                if (it.isNullOrEmpty()) showView(tv_no_results)
            })
            showProgress.observe(this@SearchActivity, Observer { showProgress(it) })
        }
    }

    private fun showProgress(show: Boolean) {
        if (show) {
            showView(progress_bar)
            hideView(tv_no_results)
        } else {
            showView(rv_news)
            hideView(progress_bar)
        }
    }
}
