package com.walvarado.unsplashtest.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.walvarado.unsplashtest.model.Exif

@Entity(
    tableName = "exif",
    foreignKeys = [ForeignKey(
        entity = PhotoDb::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("photoId"),
        onDelete = ForeignKey.CASCADE
    )]
)
class ExifDb(
    @ColumnInfo(name = "make") val make: String? = null,
    @ColumnInfo(name = "model") val model: String? = null,
    @ColumnInfo(name = "exposure_time") val exposureTime: String? = null,
    @ColumnInfo(name = "aperture") val aperture: String? = null,
    @ColumnInfo(name = "focal_length") val focalLength: String? = null,
    @ColumnInfo(name = "iso") val iso: Long? = null,
    @ColumnInfo(name = "photoId") val photoId: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    fun convertToExif(): Exif {
        return Exif(
            this.make,
            this.model,
            this.exposureTime,
            this.aperture,
            this.focalLength,
            this.iso
        )
    }
}