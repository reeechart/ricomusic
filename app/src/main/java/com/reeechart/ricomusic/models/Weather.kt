package com.reeechart.ricomusic.models

/**
 * Created by Reeechart on 06-Apr-19.
 */
class Weather(val id: Int, val main: String, val description: String, val icon: String) {
    val name: String = main
}