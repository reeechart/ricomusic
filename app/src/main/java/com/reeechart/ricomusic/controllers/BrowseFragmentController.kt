package com.reeechart.ricomusic.controllers

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import com.reeechart.ricomusic.adapters.RecommendationItemAdapter
import com.reeechart.ricomusic.fragments.BrowseFragment
import com.reeechart.ricomusic.models.Music
import com.reeechart.ricomusic.network.ricommender.RicommenderApiService
import com.reeechart.ricomusic.utils.StringChecker
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Reeechart on 06-May-19.
 */
class BrowseFragmentController(fragment: BrowseFragment) {
    companion object {
        const val RECOMMENDATION_LENGTH: Int = 20
        const val APP_NAME: String = "Ricomusic"
    }

    private val LOG_TAG = this.javaClass.simpleName

    private var browseFragment: BrowseFragment? = BrowseFragment()
    private var disposable: Disposable? = null
    private val ricommenderApiCaller by lazy {
        RicommenderApiService.create()
    }

    init {
        this.browseFragment = fragment
        retrieveRecommendation()
    }

    private fun retrieveRecommendation() {
        val preferences = browseFragment?.activity?.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE)
        val username = preferences!!.getString("username", "")
        val location = preferences.getString("location", "")
        val weather = preferences.getString("weather", "")
        Log.d(LOG_TAG, username)
        Log.d(LOG_TAG, location)
        Log.d(LOG_TAG, weather)
        if (StringChecker.isValidToGetRecommendation(username, location, weather)) {
            disposable = ricommenderApiCaller.getRecommendation(RECOMMENDATION_LENGTH, username, location.toLowerCase(), weather.toLowerCase())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { result ->
                                Log.d(LOG_TAG, result.toString())
                                hideFetchingRecommendationProgress()
                                inflateRecommendation(result)
                            },
                            { error ->
                                Log.d(LOG_TAG, error.message)
                                Toast.makeText(browseFragment?.context, error.message, Toast.LENGTH_SHORT).show()
                            }
                    )
        } else {
            Toast.makeText(browseFragment?.context, "Context invalid", Toast.LENGTH_SHORT).show()
        }
    }

    private fun hideFetchingRecommendationProgress() {
        browseFragment?.fetchingRecommendationProgress?.visibility = View.GONE
    }

    private fun inflateRecommendation(recommendations: List<Music>) {
        Log.d(LOG_TAG, "Inflating...")
        val recommendationListAdapter = RecommendationItemAdapter(browseFragment?.context, recommendations)
        this.browseFragment?.forYouSection?.visibility = View.VISIBLE
        this.browseFragment?.recommendationList?.adapter = recommendationListAdapter
        Log.d(LOG_TAG, "Inflated successfully")
    }
}