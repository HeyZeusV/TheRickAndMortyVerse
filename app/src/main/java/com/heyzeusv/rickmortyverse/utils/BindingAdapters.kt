package com.heyzeusv.rickmortyverse.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 *  DataBinding Custom Binding Adapters.
 */

/**
 *  @param imageUrl URL of image to be placed in ImageView
 */
@BindingAdapter("setGlideImage")
fun ImageView.setGlideImage(imageUrl : String?) {

    imageUrl?.let {

        Glide
            .with(context)
            .load(imageUrl)
            .into(this)
    }
}