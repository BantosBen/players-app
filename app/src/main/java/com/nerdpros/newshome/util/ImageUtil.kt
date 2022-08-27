package com.nerdpros.newshome.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nerdpros.newshome.R
import de.hdodenhof.circleimageview.CircleImageView

/**
 * @Author: Angatia Benson
 * @Date: 27/08/2022
 * Copyright (c) 2022 Bantechnis
 */

fun getProgressDrawable(_context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(_context).apply {
        strokeWidth = 2f
        centerRadius = 20f
        start()
    }
}

fun CircleImageView.loadImage(url: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions
        .placeholderOf(progressDrawable)
        .error(R.mipmap.ic_launcher)
    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}