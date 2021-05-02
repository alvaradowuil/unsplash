package com.walvarado.unsplashtest.model

import com.google.gson.annotations.SerializedName

data class UnsplashPhotoUrl (
    @SerializedName("raw") val raw: String? = null,
    @SerializedName("full") val full: String? = null,
    @SerializedName("regular") val regular: String? = null,
    @SerializedName("small") val small: String? = null,
    @SerializedName("thumb") val thumb: String? = null
)