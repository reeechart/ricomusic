package com.reeechart.ricomusic.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
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
        setLocationToView(rootView.locationName, rootView.locationIcon)
        return rootView
    }

    override fun onResume() {
        super.onResume()
        setLocationToView(view!!.locationName, view!!.locationIcon)
    }

    fun setLocationToView(locationNameLabel: TextView, locationIcon: FrameLayout) {
        val preferences: SharedPreferences = this.activity.getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
        val location: String = preferences.getString("location", null)

        when(location) {
            getString(R.string.gym) -> locationIcon.background = ContextCompat.getDrawable(this.activity, R.drawable.ic_location_gym)
            getString(R.string.office) -> locationIcon.background = ContextCompat.getDrawable(this.activity, R.drawable.ic_location_office)
            getString(R.string.canteen) -> locationIcon.background = ContextCompat.getDrawable(this.activity, R.drawable.ic_location_canteen)
            getString(R.string.library) -> locationIcon.background = ContextCompat.getDrawable(this.activity, R.drawable.ic_location_library)
            getString(R.string.travel) -> locationIcon.background = ContextCompat.getDrawable(this.activity, R.drawable.ic_location_travel)
        }

        locationNameLabel.text = location
    }
}