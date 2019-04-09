package com.reeechart.ricomusic.models

/**
 * Created by Reeechart on 06-Apr-19.
 */
object WeatherResponse {
    data class Result(val weather: List<Weather>)
}