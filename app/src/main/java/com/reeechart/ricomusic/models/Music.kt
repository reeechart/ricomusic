package com.reeechart.ricomusic.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Reeechart on 02-May-19.
 */
data class Music(@SerializedName("id") private val musicId: Int,
                 @SerializedName("title") private val musicTitle: String?,
                 @SerializedName("artist") private val musicArtist: String?,
                 @SerializedName("album") private val musicAlbum: String?,
                 @SerializedName("file") private val musicFile: String?,
                 @SerializedName("music_rank") private val musicRanking: Int?) {
    val id
        get() = musicId

    val title
        get() = musicTitle

    val artist
        get() = musicArtist

    val album
        get() = musicAlbum

    val musicRank
        get() = musicRanking
}