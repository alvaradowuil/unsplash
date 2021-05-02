package com.walvarado.unsplashtest.model

import com.google.gson.annotations.SerializedName

data class Exif (
    @SerializedName("make") val make: String? = null,
    @SerializedName("model") val model: String? = null,
    @SerializedName("exposure_time") val exposureTime: String? = null,
    @SerializedName("aperture") val aperture: String? = null,
    @SerializedName("focal_length") val focalLength: String? = null,
    @SerializedName("iso") val iso: Long? = null
)