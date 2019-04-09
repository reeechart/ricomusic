package com.reeechart.ricomusic.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.reeechart.ricomusic.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_profile.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun register(view: View) {
        val registerIntent = Intent(this, RegisterActivity::class.java)
        this.startActivity(registerIntent)
    }

    fun login(view: View) {
        val preferences: SharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
        preferences.edit().apply {
            putString("username", usernameField.text.toString())
            commit()
        }

        val pickLocationIntent = Intent(this, PickLocationActivity::class.java)
        this.startActivity(pickLocationIntent)
        this.finish()
    }
}
