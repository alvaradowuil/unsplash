package com.walvarado.unsplashtest

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class Utils {
    companion object {
        fun buildGradle(context: Context, picturePath: String, imageView: ImageView){
            val requestOption = RequestOptions()
                .placeholder(R.drawable.progress).centerCrop()

            Glide.with(context).load(picturePath).apply(requestOption).into(imageView)
        }
    }
}