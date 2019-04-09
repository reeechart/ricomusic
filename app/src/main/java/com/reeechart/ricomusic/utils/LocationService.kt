package com.reeechart.ricomusic.utils

import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.reeechart.ricomusic.fragments.ProfileFragment

/**
 * Created by Reeechart on 07-Apr-19.
 */
class LocationService: Service() {
    companion object {
        val LOCATION_UPDATE: String = "LOCATION_UPDATE"
        val LATITUDE: String = "LATITUDE"
        val LONGITUDE: String = "LONGITUDE"
    }

    private var locationManager: LocationManager? = null

    private var locationListener: LocationListener = object: LocationListener {
        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}

        override fun onProviderEnabled(p0: String?) {}

        override fun onProviderDisabled(p0: String?) {
            val enableGpsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            enableGpsIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(enableGpsIntent)
        }

        override fun onLocationChanged(location: Location) {
            var locationBroadcast = Intent(LOCATION_UPDATE)
            locationBroadcast.putExtra(LATITUDE, location.latitude)
            locationBroadcast.putExtra(LONGITUDE, location.longitude)
            sendBroadcast(locationBroadcast)
            Log.d("LOC", "${location.latitude}, ${location.longitude}")
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        locationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try {
            locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0.0F, locationListener)
            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0.0F, locationListener)
            Log.d("PASS", "PASS")
        } catch (e: SecurityException) {
            Toast.makeText(this.applicationContext, "Please enable location", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        locationManager?.removeUpdates(locationListener)
    }
}