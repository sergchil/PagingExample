package com.chilisoft.pagingexample.ui.feed

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.chilisoft.pagingexample.GlideApp
import com.chilisoft.pagingexample.R
import kotlinx.android.synthetic.main.list_item.view.*

class FeedAdapter : PagedListAdapter<FeedItem, ViewHolder>(FeedItem.DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = getItem(position) ?: return

        holder.title.text = news.title
        holder.description.text = news.description
        GlideApp.with(holder.itemView.context)
                .load(news.image)
                .into(holder.image)
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title: TextView = view.news_title
    val description: TextView = view.news_description
    val image: ImageView = view.news_image
}

data class FeedItem(
        val id: String,
        val title: String,
        val description: String,
        val image: String
) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FeedItem?>() {
            override fun areItemsTheSame(old: FeedItem, new: FeedItem): Boolean {
                return old.id === new.id
            }

            override fun areContentsTheSame(old: FeedItem, new: FeedItem): Boolean {
                return old.id == new.id
            }
        }
    }

}