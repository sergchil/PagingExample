package com.chilisoft.pagingexample.ui.feed

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chilisoft.pagingexample.FeedAdapter
import com.chilisoft.pagingexample.FeedItem
import com.chilisoft.pagingexample.R
import kotlinx.android.synthetic.main.feed_fragment.*

class FeedFragment : Fragment() {

    private var feedAdapter = FeedAdapter()

    companion object {
        fun newInstance() = FeedFragment()
    }

    private lateinit var viewModel: FeedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.feed_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.adapter = feedAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        viewModel.getArticles("Armenia", 1).observe(this, Observer { articles ->

            val items = articles?.asSequence()?.filter {
                it.title != null && it.description != null && it.urlToImage != null
            }?.map {
                FeedItem("", it.title!!, it.description!!, it.urlToImage!!)
            }?.toList() ?: emptyList()

            feedAdapter.submitList(items)
        })
    }


}
