package com.reeechart.ricomusic.activities

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
            Toast.makeText(
                    this,
                    selectedLocation.text,
                    Toast.LENGTH_SHORT
            ).show()
        }
    }
}
