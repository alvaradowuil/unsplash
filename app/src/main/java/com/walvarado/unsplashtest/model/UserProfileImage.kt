package com.walvarado.unsplashtest.model

import com.google.gson.annotations.SerializedName
import com.walvarado.unsplashtest.model.db.UserProfileImageDb

data class UserProfileImage(
    @SerializedName("small") val small: String? = null,
    @SerializedName("medium") val medium: String? = null,
    @SerializedName("large") val large: String? = null
) {
    fun convertToUserProfileImageDb(photoId: String): UserProfileImageDb {
        return UserProfileImageDb(
            this.small,
            this.medium,
            this.large,
            photoId
        )
    }
}