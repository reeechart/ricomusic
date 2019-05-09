package com.reeechart.ricomusic.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.FrameLayout
import android.widget.TextView
import com.reeechart.ricomusic.R
import com.reeechart.ricomusic.models.Music
import kotlinx.android.synthetic.main.component_music_browse.view.*
import org.w3c.dom.Text

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
    }

    override fun getItemCount(): Int {
        return musics.size
    }

    //    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
//        var convertView = view
//        if (convertView == null) {
//            convertView = layoutInflater.inflate(R.layout.component_music_list_item, parent, false)
//            Log.d(LOG_TAG, position.toString())
//
//            convertView?.musicId?.text = musics[position].id.toString()
//            convertView?.musicTitle?.text = musics[position].title
//            convertView?.musicArtist?.text = musics[position].artist
//        }
//
//        return convertView!!
//    }
//
//    override fun getItem(position: Int): Any {
//        return position
//    }
//
//    override fun getItemId(position: Int): Long {
//        return position.toLong()
//    }
//
//    override fun getCount(): Int {
//        return musics.size
//    }

    class RecommendationViewHolder(itemView: View, adapter: RecommendationItemAdapter): RecyclerView.ViewHolder(itemView) {
        var musicId = itemView.findViewById<TextView>(R.id.musicId)
        var musicTitle = itemView.findViewById<TextView>(R.id.musicTitle)
        var musicArtist = itemView.findViewById<TextView>(R.id.musicArtist)
        var musicAlbumArtwork = itemView.findViewById<FrameLayout>(R.id.musicAlbumArtwork)
        var musicRank = itemView.findViewById<TextView>(R.id.musicRank)
    }

}