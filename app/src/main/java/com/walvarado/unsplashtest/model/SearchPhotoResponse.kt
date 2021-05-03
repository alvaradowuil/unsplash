package com.walvarado.unsplashtest.model

import com.google.gson.annotations.SerializedName

data class SearchPhotoResponse(
    @SerializedName("total") val total: Int? = null,
    @SerializedName("total_pages") val totalPages: Int? = null,
    @SerializedName("results") val results: List<Photo>
)