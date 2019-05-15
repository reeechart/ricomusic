package com.reeechart.ricomusic.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.reeechart.ricomusic.R
import com.reeechart.ricomusic.models.Music
import com.reeechart.ricomusic.network.ricommender.RicommenderApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search.view.*

/**
 * Created by Richard on 05-Apr-19.
 */
class SearchFragment: Fragment() {
    private val DEBUG_TAG: String = this.javaClass.simpleName
    private var disposable: Disposable? = null

    private val ricommenderApiCaller by lazy {
        RicommenderApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)
        setSearchMusicListener(rootView)
        return rootView
    }

    private fun setSearchMusicListener(view: View) {
        view.songTitleSearchBar.setOnEditorActionListener() {v, actionId, event ->
            val title = view.songTitleSearchBar.text.toString()
            Log.d(DEBUG_TAG, title)
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (!title.isNullOrBlank()) {
                    disposable = ricommenderApiCaller.searchMusic(title)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    { result ->
                                        inflateSearchResult(result.musics)
                                    },
                                    { error ->
                                        Toast.makeText(this.context, error.message, Toast.LENGTH_SHORT).show()
                                    }
                            )
                }
                true
            }
            false
        }
    }

    private fun inflateSearchResult(musics: List<Music>) {
        Log.d(DEBUG_TAG, musics.toString())
    }
}