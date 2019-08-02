package com.sandeev.newsreader.data.repository

interface RepositoryCallback<D> {

    fun onDataSuccess(base: D)

    fun onDataError(message: String?)
}