package com.walvarado.unsplashtest.model

import com.google.gson.annotations.SerializedName

data class UnsplashUser (
    @SerializedName("id") val id: String? = null,
    @SerializedName("username") val username: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("first_name") val firstName: String? = null,
    @SerializedName("last_name") val lastName: String? = null,
    @SerializedName("portfolio_url") val portfolioURL: String? = null,
    @SerializedName("bio") val bio: String? = null,
    @SerializedName("location") val location: String? = null,
    @SerializedName("links") val links: UserLinks? = null,
    @SerializedName("profile_image") val profileImage: UserProfileImage? = null,
    @SerializedName("instagram_username") val instagramUsername: String? = null,
    @SerializedName("total_collections") val totalCollections: Long? = null,
    @SerializedName("total_likes") val totalLikes: Long? = null,
    @SerializedName("total_photos") val totalPhotos: Long? = null,
    @SerializedName("accepted_tos") val acceptedTos: Boolean? = null
)