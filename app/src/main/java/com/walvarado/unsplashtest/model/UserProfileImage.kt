package com.walvarado.unsplashtest.model

import com.google.gson.annotations.SerializedName

data class UserProfileImage (
    @SerializedName("small") val small: String? = null,
    @SerializedName("medium") val medium: String? = null,
    @SerializedName("large") val large: String? = null
)