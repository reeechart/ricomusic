package com.reeechart.ricomusic.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Reeechart on 06-May-19.
 */
class User(@SerializedName("username") val username: String,
           @SerializedName("email") val email: String?)