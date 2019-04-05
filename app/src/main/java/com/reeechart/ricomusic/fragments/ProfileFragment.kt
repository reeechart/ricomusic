package com.reeechart.ricomusic.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.reeechart.ricomusic.R
import kotlinx.android.synthetic.main.fragment_profile.view.*

/**
 * Created by Richard on 05-Apr-19.
 */
class ProfileFragment: Fragment() {
    private val DEBUG_TAG: String = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)
        setLocationToView(rootView.locationName)
        return rootView
    }

    override fun onResume() {
        super.onResume()
        setLocationToView(view!!.locationName)
    }

    fun setLocationToView(view: TextView) {
        val preferences: SharedPreferences = this.activity.getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
        val location: String = preferences.getString("location", null)

        view.text = location
    }
}