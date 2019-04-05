package com.reeechart.ricomusic.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.reeechart.ricomusic.R
import com.reeechart.ricomusic.fragments.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val DEBUG_TAG: String = this.javaClass.simpleName

    private val profileFragment: ProfileFragment = ProfileFragment()

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        when (item.itemId) {
            R.id.navigation_browse -> {
                Log.d(DEBUG_TAG, String.format("%s selected", getString(R.string.title_browse)))

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                Log.d(DEBUG_TAG, String.format("%s selected", getString(R.string.title_dashboard)))

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

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
