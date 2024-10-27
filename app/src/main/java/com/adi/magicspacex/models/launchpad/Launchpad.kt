package com.adi.magicspacex.models.launchpad

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Launchpad(
    val details: String,

    @field:Json(name = "full_name")
    val fullName: String,

    val id: String,

    val images: Images,
)