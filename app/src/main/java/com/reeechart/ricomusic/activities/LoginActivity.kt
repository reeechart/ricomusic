package com.reeechart.ricomusic.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.reeechart.ricomusic.R

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
        val pickLocationIntent = Intent(this, PickLocationActivity::class.java)
        this.startActivity(pickLocationIntent)
        this.finish()
    }
}
