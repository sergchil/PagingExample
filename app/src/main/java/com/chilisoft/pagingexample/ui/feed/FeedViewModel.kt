package com.chilisoft.pagingexample.ui.feed

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.chilisoft.pagingexample.App
import com.chilisoft.pagingexample.model.Article
import com.chilisoft.pagingexample.model.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedViewModel : ViewModel() {
    private val apiService = App.instance.authService
    private val articlesObservable: LiveData<List<Article>>

    init {
        articlesObservable = getArticles("Armenia", 1)
    }

    fun getArticles(name: String, page: Int = 0): LiveData<List<Article>> {

        val data = MutableLiveData<List<Article>>()

        apiService.getNews(name, page).enqueue(object : Callback<BaseResponse<List<Article>>?> {
            override fun onFailure(call: Call<BaseResponse<List<Article>>?>, t: Throwable) {

            }

            override fun onResponse(call: Call<BaseResponse<List<Article>>?>, response: Response<BaseResponse<List<Article>>?>) {
                if (response.isSuccessful) {
                    data.value = response.body()?.articles.orEmpty()
                }
            }
        })

        return data
    }

}
