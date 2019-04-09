package com.reeechart.ricomusic.fragments

import android.content.*
import android.location.Location
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import com.reeechart.ricomusic.R
import com.reeechart.ricomusic.network.weather.WeatherApiService
import com.reeechart.ricomusic.utils.LocationService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_profile.view.*

/**
 * Created by Richard on 05-Apr-19.
 */
class ProfileFragment: Fragment() {
    private val DEBUG_TAG: String = this.javaClass.simpleName

    private var disposable: Disposable? = null
    private var latitude: Double = Double.NaN
    private var longitude: Double = Double.NaN

    private val weatherApiCaller by lazy {
        WeatherApiService.create()
    }

    private val locationReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            latitude = intent?.getDoubleExtra(LocationService.LATITUDE, Double.NaN) ?: Double.NaN
            longitude = intent?.getDoubleExtra(LocationService.LONGITUDE, Double.NaN) ?: Double.NaN

            Log.d(DEBUG_TAG, "lat, long: $latitude, $longitude")

            disposable = weatherApiCaller.getWeather(latitude, longitude)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { result -> Toast.makeText(activity, result.weather[0].main, Toast.LENGTH_SHORT).show()},
                            { error -> Toast.makeText(activity, error.message, Toast.LENGTH_SHORT).show()}
                    )

            activity!!.unregisterReceiver(this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)
        setWeatherToView(rootView.weatherName)
        return rootView
    }

    override fun onResume() {
        super.onResume()
        setLocationToView(view!!.locationName, view!!.locationIcon)
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    private fun setLocationToView(locationNameLabel: TextView, locationIcon: FrameLayout) {
        val preferences: SharedPreferences = this.activity!!.getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
        val location: String = preferences.getString("location", null)

        when(location) {
            getString(R.string.gym) -> locationIcon.background = ContextCompat.getDrawable(this.activity!!, R.drawable.ic_location_gym)
            getString(R.string.office) -> locationIcon.background = ContextCompat.getDrawable(this.activity!!, R.drawable.ic_location_office)
            getString(R.string.canteen) -> locationIcon.background = ContextCompat.getDrawable(this.activity!!, R.drawable.ic_location_canteen)
            getString(R.string.library) -> locationIcon.background = ContextCompat.getDrawable(this.activity!!, R.drawable.ic_location_library)
            getString(R.string.travel) -> locationIcon.background = ContextCompat.getDrawable(this.activity!!, R.drawable.ic_location_travel)
        }

        locationNameLabel.text = location
    }

    private fun setWeatherToView(weatherNameLabel: TextView) {
        val locationFetchIntent = Intent(context, LocationService::class.java)
        activity!!.startService(locationFetchIntent)
        activity!!.registerReceiver(locationReceiver, IntentFilter(LocationService.LOCATION_UPDATE))
    }
}