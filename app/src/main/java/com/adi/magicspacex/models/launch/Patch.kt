package com.adi.magicspacex.models.launch

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Patch(
    val large: String,
    val small: String,
)