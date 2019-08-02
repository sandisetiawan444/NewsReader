package com.sandeev.newsreader.ui.main.news

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sandeev.newsreader.R
import com.sandeev.newsreader.ui.base.BaseFragment
import com.sandeev.newsreader.ui.main.news.detail.NewsDetailActivity
import com.sandeev.newsreader.ui.main.news.detail.NewsDetailActivity.Companion.EXTRA_ARTICLE
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.coroutines.launch

class NewsFragment : BaseFragment() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var mainAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSwipeRefresh()
        setRecyclerView()
        hideView(rv_news)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java).apply {
            showProgress.observe(this@NewsFragment, Observer { swipe_refresh.isRefreshing = it })
            articles.observe(this@NewsFragment, Observer {
                showView(rv_news)
                mainAdapter.setArticles(it)
            })
            launch { setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1) }
        }
    }

    private fun setSwipeRefresh() {
        swipe_refresh.setOnRefreshListener {
            launch { viewModel.setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1) }
        }
    }

    private fun setRecyclerView() {
        mainAdapter = NewsAdapter().apply {
            onItemClick = {
                startActivity(
                    Intent(context, NewsDetailActivity::class.java)
                        .putExtra(EXTRA_ARTICLE, it)
                )
            }
        }

        rv_news.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mainAdapter
        }
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): NewsFragment {
            return NewsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}
