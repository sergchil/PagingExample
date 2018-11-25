package com.chilisoft.pagingexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.chilisoft.pagingexample.ui.feed.FeedFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, FeedFragment.newInstance())
                    .commitNow()
        }
    }

}