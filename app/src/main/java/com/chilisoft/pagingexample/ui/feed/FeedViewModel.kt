package com.chilisoft.pagingexample.ui.feed

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PageKeyedDataSource
import android.arch.paging.PagedList
import com.chilisoft.pagingexample.App
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class FeedViewModel : ViewModel() {


    private val executor: Executor = Executors.newFixedThreadPool(5)
    private var newsFeedObservable: LiveData<PagedList<FeedItem>>

    init {

        val feedDataFactory = FeedDataSourceFactory()
        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(20)
                .build()


        newsFeedObservable = LivePagedListBuilder(feedDataFactory, pagedListConfig)
                .setFetchExecutor(executor)
                .build()
    }

    fun getNewsFeed(): LiveData<PagedList<FeedItem>> {
        return newsFeedObservable
    }

    private class FeedDataSource : PageKeyedDataSource<Int, FeedItem>() {
        private val apiService = App.instance.apiService

        override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, FeedItem>) {
            val items = getNewsFeed("bitcoin", 1)
            callback.onResult(items, null, 2)
        }

        override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, FeedItem>) {
            val items = getNewsFeed("bitcoin", params.key)
            callback.onResult(items, params.key + 1)
        }

        override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, FeedItem>) {

        }

        private fun getNewsFeed(name: String, page: Int): List<FeedItem> {
            val response = apiService.getNews(name, page).execute()
            var items = emptyList<FeedItem>()
            if (response.isSuccessful) {
                val articles = response.body()?.articles ?: return items

                items = articles.asSequence().filter {
                    it.title != null && it.description != null && it.urlToImage != null
                }.map {
                    FeedItem(UUID.nameUUIDFromBytes(it.title!!.toByteArray()).toString(), it.title, it.description!!, it.urlToImage!!)
                }.toList()
            }

            return items
        }
    }

    private class FeedDataSourceFactory : DataSource.Factory<Int, FeedItem>() {
        override fun create(): DataSource<Int, FeedItem> {
            return FeedDataSource()
        }
    }
}
