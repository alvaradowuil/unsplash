package com.walvarado.unsplashtest.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.walvarado.unsplashtest.model.UserLinks

@Entity(
    tableName = "userLinks",
    foreignKeys = [ForeignKey(
        entity = UserDb::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("userId"),
        onDelete = ForeignKey.CASCADE
    )]
)
class UserLinksDb(
    @ColumnInfo(name = "self") val self: String? = null,
    @ColumnInfo(name = "html") val html: String? = null,
    @ColumnInfo(name = "photos") val photos: String? = null,
    @ColumnInfo(name = "likes") val likes: String? = null,
    @ColumnInfo(name = "portfolio") val portfolio: String? = null,
    @ColumnInfo(name = "following") val following: String? = null,
    @ColumnInfo(name = "followers") val followers: String? = null,
    @ColumnInfo(name = "userId") val userId: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    fun convertToUserLinks(): UserLinks {
        return UserLinks(
            this.self,
            this.html,
            this.photos,
            this.likes,
            this.portfolio,
            this.following,
            this.followers
        )
    }
}