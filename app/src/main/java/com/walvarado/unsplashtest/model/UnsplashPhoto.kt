package com.walvarado.unsplashtest.model

import com.google.gson.annotations.SerializedName

data class UnsplashPhoto (
    @SerializedName("id") val id: String? = null,
    @SerializedName("width") val width: Long? = null,
    @SerializedName("height") val height: Long? = null,
    @SerializedName("color") val color: String? = null,
    @SerializedName("blur_hash") val blurHash: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("urls") val urls: UnsplashPhotoUrl? = null,
    @SerializedName("likes") val likes: Long? = null,
    @SerializedName("downloads") val downloads: Long? = null,
    @SerializedName("user") val user: UnsplashUser? = null,
    @SerializedName("exif") val exif: Exif? = null
)