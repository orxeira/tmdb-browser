package com.orxeira.tmdb_browser.ui.main.adapter

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.paging.PagingData
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.orxeira.tmdb_browser.R
import com.orxeira.tmdb_browser.common.loadUrl
import com.orxeira.tmdb_browser.domain.TvShow

@BindingAdapter("imageUrl")
fun ImageView.bindImageItem(url: String?) {
    if (url != null) loadUrl(url)
}

@BindingAdapter("toVisibility")
fun View.toVisibility(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("ImageUrl")
fun bindImageItem(cardView: CardView, url: String?) {
    Glide.with(cardView.context)
        .asBitmap()
        .load(url)
        .apply(RequestOptions().centerCrop())
        .into(object : BitmapImageViewTarget(cardView.findViewById(R.id.item_poster)) {
            override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                super.onResourceReady(bitmap, transition)
                Palette.from(bitmap).generate { palette ->
                    val color = palette!!.getVibrantColor(
                        ContextCompat.getColor(cardView.context,
                            R.color.black_translucent_60))

                    cardView.findViewById<View>(R.id.title_background).setBackgroundColor(color)
                }
            }
        })
}