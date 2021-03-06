package com.reeechart.ricomusic.activities

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentTransaction
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.reeechart.ricomusic.R
import com.reeechart.ricomusic.fragments.BrowseFragment
import com.reeechart.ricomusic.fragments.ProfileFragment
import com.reeechart.ricomusic.fragments.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val DEBUG_TAG: String = this.javaClass.simpleName

    private val browseFragment: BrowseFragment = BrowseFragment()
    private val searchFragment: SearchFragment = SearchFragment()
    private val profileFragment: ProfileFragment = ProfileFragment()

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        when (item.itemId) {
            R.id.navigation_browse -> {
                Log.d(DEBUG_TAG, String.format("%s selected", getString(R.string.title_browse)))

                transaction.replace(R.id.fragmentContainer, browseFragment)
                transaction.commit()

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {
                Log.d(DEBUG_TAG, String.format("%s selected", getString(R.string.title_search)))

                transaction.replace(R.id.fragmentContainer, searchFragment)
                transaction.commit()

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                Log.d(DEBUG_TAG, String.format("%s selected", getString(R.string.title_profile)))

                transaction.replace(R.id.fragmentContainer, profileFragment)
                transaction.commit()

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, BrowseFragment())
        transaction.commit()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        checkRuntimePermissions()
    }

    fun logout(view: View) {
        val preferences: SharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
        preferences.edit().remove("username").apply()
        val logoutIntent = Intent(this, LoginActivity::class.java)
        this.startActivity(logoutIntent)
        this.finish()
    }

    fun changeLocation(view: View) {
        val changeLocationIntent = Intent(this, PickLocationActivity::class.java)
        this.startActivity(changeLocationIntent)
    }

    fun refreshWeather(view: View) {
        profileFragment.setWeatherToView()
    }

    private fun checkRuntimePermissions() {
        if (Build.VERSION.SDK_INT >= 21 && isLocationDisabled()) {
            Log.d(DEBUG_TAG, "Need permission")
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                    99)
        } else {
            Log.d(DEBUG_TAG, "Permission passed")
        }
    }

    private fun isLocationDisabled(): Boolean {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
    }
}
