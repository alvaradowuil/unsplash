package com.walvarado.unsplashtest.model

import com.google.gson.annotations.SerializedName
import com.walvarado.unsplashtest.model.db.PhotoUrlDb

data class PhotoUrl (
    @SerializedName("raw") val raw: String? = null,
    @SerializedName("full") val full: String? = null,
    @SerializedName("regular") val regular: String? = null,
    @SerializedName("small") val small: String? = null,
    @SerializedName("thumb") val thumb: String? = null
) {
    fun convertToPhotoUrlDb(photoId: String): PhotoUrlDb {
        return PhotoUrlDb(
            this.raw,
            this.full,
            this.regular,
            this.small,
            this.thumb,
            photoId
        )
    }
}