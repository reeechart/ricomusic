package com.reeechart.ricomusic.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.reeechart.ricomusic.R
import com.reeechart.ricomusic.activities.NowPlayingActivity
import com.reeechart.ricomusic.models.Music

/**
 * Created by Reeechart on 06-May-19.
 */
class RecommendationItemAdapter(ctx: Context?, musicList: List<Music>): RecyclerView.Adapter<RecommendationItemAdapter.RecommendationViewHolder>() {
    private val LOG_TAG: String = this.javaClass.simpleName

    private val context: Context? = ctx
    private val musics: List<Music> = musicList
    private var layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationViewHolder {
        val itemView = layoutInflater.inflate(R.layout.component_music_list_item, parent, false)
        return RecommendationViewHolder(itemView, this)
    }

    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        val music = musics[position]
        holder.musicId.text = music.id.toString()
        holder.musicRank.text = music.musicRank.toString()
        holder.musicTitle.text = music.title
        holder.musicArtist.text = music.artist
        holder.parentView.setOnClickListener(View.OnClickListener {
            val playMusicIntent = Intent(context, NowPlayingActivity::class.java)
            context?.startActivity(playMusicIntent)
        })
    }

    override fun getItemCount(): Int {
        return musics.size
    }

    inner class RecommendationViewHolder(itemView: View, adapter: RecommendationItemAdapter): RecyclerView.ViewHolder(itemView) {
        var mAdapter = adapter
        var parentView = itemView
        var musicId = itemView.findViewById<TextView>(R.id.musicId)
        var musicTitle = itemView.findViewById<TextView>(R.id.musicTitle)
        var musicArtist = itemView.findViewById<TextView>(R.id.musicArtist)
        var musicAlbumArtwork = itemView.findViewById<FrameLayout>(R.id.musicAlbumArtwork)
        var musicRank = itemView.findViewById<TextView>(R.id.musicRank)

        fun bindWithListener(item: Music, listener: View.OnClickListener) {
            parentView.setOnClickListener({
                val playMusicIntent: Intent = Intent(context, NowPlayingActivity::class.java)
                context?.startActivity(playMusicIntent)
            })
        }
    }

}