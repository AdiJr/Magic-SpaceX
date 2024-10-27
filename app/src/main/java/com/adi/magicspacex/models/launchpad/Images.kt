package com.adi.magicspacex.models.launchpad

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Images(
    val large: List<String>
)