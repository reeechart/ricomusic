package com.reeechart.ricomusic.activities

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import kotlinx.android.synthetic.main.activity_now_playing.*

class NowPlayingActivity : AppCompatActivity() {
    private val DEBUG_TAG: String = this.javaClass.simpleName
    companion object {
        private const val SAMPLE_URI = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
        private const val RICOMMENDER_URI = "http://157.230.243.204/music/stream/"
    }

    private lateinit var musicPlayer: SimpleExoPlayer
    private var musicId: Int = 0
    private var musicRank: Int = 0
    private var musicTitle: String = ""
    private var musicArtist: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_now_playing)

        musicId = intent.getIntExtra("musicId", 0)
        musicRank = intent.getIntExtra("musicRank", 0)
        musicTitle = intent.getStringExtra("musicTitle")
        musicArtist = intent.getStringExtra("musicArtist")

//        val playMusicIntent = Intent(this, AudioPlayerService::class.java)
//        Util.startForegroundService(this, playMusicIntent)
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
        return RICOMMENDER_URI + musicId.toString()
    }
}
