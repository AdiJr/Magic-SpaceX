package com.adi.magicspacex.models.dragon

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Dragon(
    val description: String,

    @field:Json(name = "flickr_images")
    val images: List<String>,

    val id: String,

    val name: String,
)