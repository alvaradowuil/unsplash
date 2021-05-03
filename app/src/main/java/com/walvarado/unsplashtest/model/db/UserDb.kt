package com.walvarado.unsplashtest.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.walvarado.unsplashtest.model.User
import com.walvarado.unsplashtest.model.UserLinks
import com.walvarado.unsplashtest.model.UserProfileImage

@Entity(
    tableName = "user",
    foreignKeys = [ForeignKey(
        entity = PhotoDb::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("photoId"),
        onDelete = ForeignKey.CASCADE
    )]
)
class UserDb (
    @PrimaryKey() val id: String,
    @ColumnInfo(name = "username") val username: String? = null,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "first_name") val firstName: String? = null,
    @ColumnInfo(name = "last_name") val lastName: String? = null,
    @ColumnInfo(name = "portfolio_url") val portfolioURL: String? = null,
    @ColumnInfo(name = "bio") val bio: String? = null,
    @ColumnInfo(name = "location") val location: String? = null,
    @ColumnInfo(name = "instagram_username") val instagramUsername: String? = null,
    @ColumnInfo(name = "total_collections") val totalCollections: Long? = null,
    @ColumnInfo(name = "total_likes") val totalLikes: Long? = null,
    @ColumnInfo(name = "total_photos") val totalPhotos: Long? = null,
    @ColumnInfo(name = "accepted_tos") val acceptedTos: Boolean? = null,
    @ColumnInfo(name = "photoId") val photoId: String? = null
) {
    fun convertToUser(userLinks: UserLinks, profileImage: UserProfileImage): User {
        return User(
            this.id,
            this.username,
            this.name,
            this.firstName,
            this.lastName,
            this.portfolioURL,
            this.bio,
            this.location,
            userLinks,
            profileImage,
            this.instagramUsername,
            this.totalCollections,
            this.totalLikes,
            this.totalPhotos,
            this.acceptedTos
        )
    }
}