package com.reeechart.ricomusic.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Reeechart on 12-May-19.
 */
class History(@SerializedName("user") val username: String,
              @SerializedName("location") val location: String,
              @SerializedName("weather") val weather: String,
              @SerializedName("music") val musicId: Int,
              @SerializedName("music_rank") val musicRank: Int) {
}