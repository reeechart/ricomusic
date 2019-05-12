package com.reeechart.ricomusic.activities

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.reeechart.ricomusic.R
import com.reeechart.ricomusic.models.History
import com.reeechart.ricomusic.network.ricommender.RicommenderApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_now_playing.*
import kotlinx.android.synthetic.main.component_music_player.*

class NowPlayingActivity : AppCompatActivity() {
    private val DEBUG_TAG: String = this.javaClass.simpleName

    private var disposable: Disposable? = null

    companion object {
        private const val SAMPLE_URI = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
        private const val RICOMMENDER_URI = "http://157.230.243.204/music/stream/"
    }

    private val ricommenderApiCaller by lazy {
        RicommenderApiService.create()
    }

    private lateinit var musicPlayer: SimpleExoPlayer
    private var playedMusicId: Int = 0
    private var playedMusicRank: Int = 0
    private var playedMusicTitle: String = ""
    private var playedMusicArtist: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_now_playing)

        playedMusicId = intent.getIntExtra("musicId", 0)
        playedMusicRank = intent.getIntExtra("musicRank", 0)
        playedMusicTitle = intent.getStringExtra("musicTitle")
        playedMusicArtist = intent.getStringExtra("musicArtist")

//        val playMusicIntent = Intent(this, AudioPlayerService::class.java)
//        Util.startForegroundService(this, playMusicIntent)
        addMusicToHistory()
        setMusicMetadataToView()

        initializePlayer()

        val dataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, getString(R.string.app_name)))

        val musicURL = getMusicURL()
        val mediaSource: MediaSource = ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(musicURL))

        musicPlayer.prepare(mediaSource)
        musicPlayer.playWhenReady = true

        musicPlayerView.player = musicPlayer
    }

    override fun onDestroy() {
        Log.d(DEBUG_TAG, "NowPlaying destroy")
        releasePlayer()
        super.onDestroy()
    }

    private fun initializePlayer() {
        val trackSelector = DefaultTrackSelector()
        val loadControl = DefaultLoadControl()
        val renderersFactory =  DefaultRenderersFactory(applicationContext)

        musicPlayer = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl)
    }

    private fun releasePlayer() {
        musicPlayer.stop()
        musicPlayer.release()
    }

    private fun getMusicURL(): String {
        return RICOMMENDER_URI + playedMusicId.toString()
    }

    private fun addMusicToHistory() {
        val preferences: SharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
        val username: String = preferences.getString("username", "")
        val location: String = preferences.getString("location", "").toLowerCase()
        val weather: String = preferences.getString("weather", "").toLowerCase()
        val history = History(username, location, weather, playedMusicId, playedMusicRank)
        Log.d(DEBUG_TAG, username)
        Log.d(DEBUG_TAG, location)
        Log.d(DEBUG_TAG, weather)
        Log.d(DEBUG_TAG, playedMusicId.toString())
        Log.d(DEBUG_TAG, playedMusicRank.toString())
        disposable = ricommenderApiCaller.addHistory(history)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->

                        },
                        { error ->
                            Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                        }
                )
    }

    private fun setMusicMetadataToView() {
        musicTitle.text = playedMusicTitle
        musicArtist.text = playedMusicArtist
        musicTitle.isSelected = true
        musicArtist.isSelected = true
    }
}
