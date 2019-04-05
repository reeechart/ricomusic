package com.reeechart.ricomusic.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.reeechart.ricomusic.R
import kotlinx.android.synthetic.main.activity_pick_location.*

class PickLocationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_location)
    }

    fun confirmLocation(view: View) {
        val selectedLocationId = locationPicker.checkedRadioButtonId
        val selectedLocation = findViewById<RadioButton>(selectedLocationId)

        if (selectedLocation == null) {
            Toast.makeText(
                    this,
                    "Please select one location",
                    Toast.LENGTH_SHORT
            ).show()
        } else {
            val preferences: SharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
            var editor = preferences.edit().apply {
                putString("location", selectedLocation.text.toString())
                commit()
            }

            Toast.makeText(
                    this,
                    String.format("You selected: %s", selectedLocation.text),
                    Toast.LENGTH_SHORT
            ).show()

            val mainActivityIntent = Intent(this, MainActivity::class.java)
            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            this.startActivity(mainActivityIntent)
            this.finish()
        }
    }
}
