package com.chilisoft.pagingexample.ui.feed

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chilisoft.pagingexample.R
import kotlinx.android.synthetic.main.feed_fragment.*

class FeedFragment : Fragment() {
    private lateinit var viewModel: FeedViewModel
    private var feedAdapter = FeedAdapter()

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
        viewModel.getNewsFeed().observe(this, Observer(feedAdapter::submitList))
    }

    companion object {
        fun newInstance() = FeedFragment()
    }

}

