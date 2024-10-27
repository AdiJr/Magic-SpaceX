package com.adi.magicspacex.models.ship

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Ship(
    val id: String,
    val image: String,
    val name: String,
)