package com.walvarado.unsplashtest.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.walvarado.unsplashtest.model.UserProfileImage

@Entity(
    tableName = "userProfileImage",
    foreignKeys = [ForeignKey(
        entity = UserDb::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("userId"),
        onDelete = ForeignKey.CASCADE
    )]
)
class UserProfileImageDb(
    @ColumnInfo(name = "small") val small: String? = null,
    @ColumnInfo(name = "medium") val medium: String? = null,
    @ColumnInfo(name = "large") val large: String? = null,
    @ColumnInfo(name = "userId") val userId: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    fun convertToProfileImage(): UserProfileImage {
        return UserProfileImage(
            this.small,
            this.medium,
            this.large
        )
    }
}