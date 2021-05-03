package com.walvarado.unsplashtest.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.walvarado.unsplashtest.model.PhotoUrl

@Entity(
    tableName = "photoUrl",
    foreignKeys = [ForeignKey(
        entity = PhotoDb::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("photoId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class PhotoUrlDb(
    @ColumnInfo(name = "raw") val raw: String? = null,
    @ColumnInfo(name = "full") val full: String? = null,
    @ColumnInfo(name = "regular") val regular: String? = null,
    @ColumnInfo(name = "small") val small: String? = null,
    @ColumnInfo(name = "thumb") val thumb: String? = null,
    @ColumnInfo(name = "photoId") val photoId: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    fun convertToPhotoUrl(): PhotoUrl {
        return PhotoUrl(
            this.raw,
            this.full,
            this.regular,
            this.small,
            this.thumb
        )
    }
}