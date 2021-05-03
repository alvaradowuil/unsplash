package com.walvarado.unsplashtest.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.walvarado.unsplashtest.model.Exif
import com.walvarado.unsplashtest.model.Photo
import com.walvarado.unsplashtest.model.PhotoUrl
import com.walvarado.unsplashtest.model.User

@Entity(
    tableName = "photo"
)
data class PhotoDb (
    @PrimaryKey() val id: String,
    @ColumnInfo(name = "width") val width: Long? = null,
    @ColumnInfo(name = "height") val height: Long? = null,
    @ColumnInfo(name = "color") val color: String? = null,
    @ColumnInfo(name = "blur_hash") val blurHash: String? = null,
    @ColumnInfo(name = "description") val description: String? = null,
    @ColumnInfo(name = "likes") val likes: Long? = null,
    @ColumnInfo(name = "downloads") val downloads: Long? = null
) {
    fun convertToPhoto(): Photo {
        return Photo(
            this.id,
            this.width,
            this.height,
            this.color,
            this.blurHash,
            this.description,
            null,
            this.likes,
            this.downloads,
            null,
            null
        )
    }
}