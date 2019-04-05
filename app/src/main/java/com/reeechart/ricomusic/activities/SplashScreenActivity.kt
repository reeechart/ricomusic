package com.reeechart.ricomusic.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import com.reeechart.ricomusic.R

class SplashScreenActivity : AppCompatActivity() {
    companion object {
        private val DEBUG_TAG: String = this.javaClass.simpleName
        private val SPLASH_SCREEN_DURATION: Int = 2000
    }

    private val activityChangeHandler = Handler()

    private val activityChangeRunnable = Runnable {
        val preferences: SharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
        val username: String? = preferences.getString("username", null)

        Log.d(DEBUG_TAG, "Checking authentication...")

        var nextActivityIntent: Intent? = null
        if (username.isNullOrEmpty()) {
            Log.d(DEBUG_TAG, "Username not found")
            nextActivityIntent = Intent(this, LoginActivity::class.java)
        } else {
            Log.d(DEBUG_TAG, "Username found")
            nextActivityIntent = Intent(this, PickLocationActivity::class.java)
        }

        this.startActivity(nextActivityIntent)
        this.finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        checkLoginWithDelay(SPLASH_SCREEN_DURATION)
    }

    private fun checkLoginWithDelay(delayMillis: Int) {
        activityChangeHandler.removeCallbacks(activityChangeRunnable)
        activityChangeHandler.postDelayed(activityChangeRunnable, delayMillis.toLong())
    }
}
