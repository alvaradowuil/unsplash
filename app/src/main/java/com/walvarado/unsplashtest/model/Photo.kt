package com.walvarado.unsplashtest.model

import com.google.gson.annotations.SerializedName
import com.walvarado.unsplashtest.model.db.PhotoDb

data class Photo(
    @SerializedName("id") val id: String? = null,
    @SerializedName("width") val width: Long? = null,
    @SerializedName("height") val height: Long? = null,
    @SerializedName("color") val color: String? = null,
    @SerializedName("blur_hash") val blurHash: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("urls") var urls: PhotoUrl? = null,
    @SerializedName("likes") val likes: Long? = null,
    @SerializedName("downloads") val downloads: Long? = null,
    @SerializedName("user") var user: User? = null,
    @SerializedName("exif") var exif: Exif? = null
) {
    fun convertToPhotoDb(): PhotoDb {
        return PhotoDb(
            this.id!!,
            this.width,
            this.height,
            this.color,
            this.blurHash,
            this.description,
            this.likes,
            this.downloads
        )
    }
}