package com.sandeev.newsreader.ui.main.news

import android.annotation.SuppressLint
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sandeev.newsreader.R
import com.sandeev.newsreader.model.Article
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_news.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private var articles = ArrayList<Article>()
    private var headlineEnabled = true
    private val viewTypeFirst = 1

    var onItemClick: ((Article) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                if (viewType == viewTypeFirst) R.layout.item_news_big else R.layout.item_news,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.onBind(position)

    override fun getItemViewType(position: Int): Int =
        if (position == 0 && headlineEnabled) viewTypeFirst else 0

    fun setArticles(articles: List<Article>?) {
        articles?.let {
            this.articles = it as ArrayList<Article>
            notifyDataSetChanged()
        }
    }

    fun setHeadlineEnable(enable: Boolean) {
        this.headlineEnabled = enable
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(position: Int) = inflateData(articles[position])

        @SuppressLint("SetTextI18n")
        private fun inflateData(article: Article?) {
            article?.let {
                Picasso.get().load(it.urlToImage).fit().centerCrop().into(itemView.img_news)
                itemView.tv_title.text = it.title
                itemView.tv_description.text = it.description
                itemView.tv_source.text = it.source?.name

                itemView.layout_container.setOnClickListener { onItemClick?.invoke(article) }
            }
            article?.publishedAt?.let {
                itemView.tv_source.text = "${article.source?.name} ∙ ${getRelativeTimeSpan(it)}"
            }
        }

        private fun getRelativeTimeSpan(dateString: String): String? {
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("GMT")
            }.parse(dateString)?.time?.let {
                return DateUtils.getRelativeTimeSpanString(
                    it,
                    System.currentTimeMillis(),
                    0
                ) as String
            }

            return null
        }
    }
}