package com.heyzeusv.rickmortyverse.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 *  DataBinding Custom Binding Adapters.
 */
@BindingAdapter("setGlideImage")
fun ImageView.setGlideImage(imageUrl : String) {

    Glide
        .with(context)
        .load(imageUrl)
        .into(this)
}