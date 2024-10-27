package com.adi.magicspacex.models.rocket

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Rocket(
    val description: String,

    @field:Json(name = "flickr_images")
    val images: List<String>,

    val id: String,

    val name: String,
)