package com.reeechart.ricomusic.fragments

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.reeechart.ricomusic.R
import com.reeechart.ricomusic.controllers.BrowseFragmentController

/**
 * Created by Richard on 05-Apr-19.
 */

class BrowseFragment: Fragment() {
    lateinit var recommendationList: RecyclerView
    lateinit var recommendationCalculationText: TextView
    lateinit var forYouSection: ConstraintLayout
    lateinit var fetchingRecommendationProgress: ConstraintLayout
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
        forYouSection = rootView.findViewById(R.id.forYouSection)
        fetchingRecommendationProgress = rootView.findViewById(R.id.fetchingRecommendationProgress)
        recommendationCalculationText = rootView.findViewById(R.id.recommendationCalculationText)
        randomizeRecommendationCalculationText()
        recommendationList = rootView.findViewById(R.id.recommendationList)
        linearLayoutManager = LinearLayoutManager(this.context)
        recommendationList.layoutManager = linearLayoutManager
        return rootView
    }

    private fun randomizeRecommendationCalculationText() {
        val randomNumber: Int = (1..5).shuffled().first()
        recommendationCalculationText.text = when (randomNumber) {
            1 -> getString(R.string.recommendation_is_on_its_way)
            2 -> getString(R.string.wait_recommendation_is_coming)
            3 -> getString(R.string.standby_and_get_your_recommendation)
            4 -> getString(R.string.multiplying_this_and_this_and_that)
            else -> getString(R.string.packaging_your_recommendation)
        }
    }
}