package com.sandeev.newsreader.ui.main.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sandeev.newsreader.data.repository.RemoteRepository
import com.sandeev.newsreader.data.repository.RepositoryCallback
import com.sandeev.newsreader.model.Article
import com.sandeev.newsreader.model.BaseResponse
import com.sandeev.newsreader.util.AppConstants

class NewsViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()
    private val _articles = MutableLiveData<List<Article>>()
    private val _showProgress = MutableLiveData<Boolean>()

    val articles: LiveData<List<Article>> = Transformations.map(_articles) { it }
    val showProgress: LiveData<Boolean> = Transformations.map(_showProgress) { it }

    suspend fun setIndex(index: Int) {
        _index.value = index

        when (index) {
            AppConstants.CATEGORY_GENERAL -> loadArticles(AppConstants.API_CATEGORY_GENERAL)
            AppConstants.CATEGORY_TECHNOLOGY -> loadArticles(AppConstants.API_CATEGORY_TECHNOLOGY)
            AppConstants.CATEGORY_ENTERTAINMENT -> loadArticles(AppConstants.API_CATEGORY_ENTERTAINMENT)
            AppConstants.CATEGORY_SPORTS -> loadArticles(AppConstants.API_CATEGORY_SPORTS)
        }
    }

    private suspend fun loadArticles(category: String?) {
        _showProgress.postValue(true)

        RemoteRepository().getArticles(
            null,
            AppConstants.API_COUNTRY,
            category,
            AppConstants.API_KEY,
            object :
                RepositoryCallback<BaseResponse?> {
                override fun onDataSuccess(base: BaseResponse?) {
                    _showProgress.postValue(false)
                    base?.articles?.let { _articles.postValue(it) }
                }

                override fun onDataError(message: String?) {
                    _showProgress.postValue(false)
                    Log.e("Exception: ", message.toString())
                }
            })
    }
}
