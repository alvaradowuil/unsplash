package com.walvarado.unsplashtest.model.db

import androidx.room.Embedded
import androidx.room.Relation

data class UserAndLinks(
    @Embedded val userDb: UserDb,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId",
        entity = UserLinksDb::class
    ) val userLinksDb: UserLinksDb? = null,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId",
        entity = UserProfileImageDb::class
    ) val userProfileImageDb: UserProfileImageDb? = null
)