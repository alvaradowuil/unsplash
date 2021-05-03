package com.walvarado.unsplashtest.model.db

import androidx.room.Embedded
import androidx.room.Relation

data class PhotoAndUser(
    @Embedded val photo: PhotoDb,
    @Relation(
        parentColumn = "id",
        entityColumn = "photoId",
        entity = PhotoUrlDb::class
    )
    val photoUrlDb: PhotoUrlDb? = null,
    @Relation(
        parentColumn = "id",
        entityColumn = "photoId",
        entity = ExifDb::class

    )
    val exif: ExifDb? = null,
    @Relation(
        parentColumn = "id",
        entityColumn = "photoId",
        entity = UserDb::class
    )
    var user: UserAndLinks? = null
)