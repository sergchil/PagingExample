package com.chilisoft.pagingexample

import com.chilisoft.pagingexample.model.Article
import com.chilisoft.pagingexample.model.BaseResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("everything")
    fun getNews(@Query("q") name: String, @Query("page") user: Int): Call<BaseResponse<List<Article>>>

    companion object {
        fun create(): ApiService {
            return App.instance.retorfit.create(ApiService::class.java)
        }
    }
}