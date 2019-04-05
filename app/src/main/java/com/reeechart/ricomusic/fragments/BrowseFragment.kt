package com.reeechart.ricomusic.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.reeechart.ricomusic.R
import kotlinx.android.synthetic.main.fragment_browse.view.*

/**
 * Created by Richard on 05-Apr-19.
 */

class BrowseFragment: Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_browse, container, false)
        rootView.text.setText(R.string.title_browse)
        return rootView
    }
}