package com.sandeev.newsreader.ui.main.news.detail

import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.sandeev.newsreader.R
import com.sandeev.newsreader.model.Article
import com.sandeev.newsreader.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_news_detail.*

class NewsDetailActivity : BaseActivity() {

    companion object {
        const val EXTRA_ARTICLE = "article"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        val article = intent.getParcelableExtra<Article?>(EXTRA_ARTICLE)

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = article?.title
            setDisplayHomeAsUpEnabled(true)
        }

        setWebView(article)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setWebView(article: Article?) {
        web_view.apply {
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    showView(progress_bar)
                }

                override fun onPageCommitVisible(view: WebView?, url: String?) {
                    hideView(progress_bar)
                }
            }
            loadUrl(article?.url)
        }
    }
}
