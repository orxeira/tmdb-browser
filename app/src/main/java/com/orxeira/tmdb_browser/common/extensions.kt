package com.orxeira.tmdb_browser.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orxeira.tmdb_browser.domain.TvShow

/**
 * Simplify RecyclerView item inflation
 */
fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = true): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

/**
 * Add the given LoadStateAdapter to a RecyclerViewAdapter.
 */
fun <T : Any, V : RecyclerView.ViewHolder> PagingDataAdapter<T, V>.withLoadStateAdapters(
    header: LoadStateAdapter<*>,
    footer: LoadStateAdapter<*>
): ConcatAdapter {
    addLoadStateListener { loadStates ->
        header.loadState = loadStates.refresh
        footer.loadState = loadStates.append
    }

    return ConcatAdapter(header, this, footer)
}

/**
 * This extensions sortes the TvShows by vote average, adds the argument TvShow as the first item
 * and deletes any possible duplicates.
 */
fun List<TvShow>.addOriginal(tvShow: TvShow): List<TvShow> {
    val list = this.sortedByDescending { it.voteAverage }.toMutableList()
    list.add(0, tvShow)
    return list.distinctBy { it.id }
}

/**
 * Given a toolbar, set it as action bar an run a block function on it.
 */
fun AppCompatActivity.setupActionBar(toolbar: Toolbar, action: ActionBar.() -> Unit) {
    setSupportActionBar(toolbar)
    supportActionBar?.run {
        action()
    }
}