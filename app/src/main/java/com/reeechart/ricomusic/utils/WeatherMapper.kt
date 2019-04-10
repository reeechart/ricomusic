package com.reeechart.ricomusic.utils

import android.util.Log

/**
 * Created by Reeechart on 07-Apr-19.
 */
class WeatherMapper {
    companion object {
        const val WEATHER_RAIN: String = "Rain"
        const val WEATHER_CLOUDY: String = "Cloudy"
        const val WEATHER_SUNNY: String = "Sunny"
        const val WEATHER_ERROR: String = "Error"

        fun mapWeather(inputCode: Int): String {
            val firstDigit: Int = inputCode.div(100)
            val lastDigit: Int = inputCode.rem(10)
            val weather = when (firstDigit) {
                2, 3, 4, 5, 6 -> WEATHER_RAIN
                7 -> WEATHER_CLOUDY
                8 -> if (lastDigit == 0) {
                    WEATHER_SUNNY
                } else {
                    WEATHER_CLOUDY
                }
                else -> WEATHER_ERROR
            }

            return weather
        }
    }
}