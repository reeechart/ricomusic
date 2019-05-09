package com.reeechart.ricomusic.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.reeechart.ricomusic.R
import com.reeechart.ricomusic.controllers.BrowseFragmentController

/**
 * Created by Richard on 05-Apr-19.
 */

class BrowseFragment: Fragment() {
    lateinit var recommendationList: RecyclerView
    private lateinit var controller: BrowseFragmentController
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        controller = BrowseFragmentController(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_browse, container, false)
        recommendationList = rootView.findViewById(R.id.recommendationList)
        linearLayoutManager = LinearLayoutManager(this.context)
        recommendationList.layoutManager = linearLayoutManager
        return rootView
    }
}