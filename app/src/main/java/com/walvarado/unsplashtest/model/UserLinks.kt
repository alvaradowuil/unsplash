package com.walvarado.unsplashtest.model

import com.google.gson.annotations.SerializedName
import com.walvarado.unsplashtest.model.db.UserLinksDb

data class UserLinks(
    @SerializedName("self") val self: String? = null,
    @SerializedName("html") val html: String? = null,
    @SerializedName("photos") val photos: String? = null,
    @SerializedName("likes") val likes: String? = null,
    @SerializedName("portfolio") val portfolio: String? = null,
    @SerializedName("following") val following: String? = null,
    @SerializedName("followers") val followers: String? = null
) {
    fun convertToUserLinksDb(photoId: String): UserLinksDb {
        return UserLinksDb(
            this.self,
            this.html,
            this.photos,
            this.likes,
            this.portfolio,
            this.following,
            this.followers,
            photoId
        )
    }
}