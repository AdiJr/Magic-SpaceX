package com.adi.magicspacex.models.launch

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Flickr(
    val original: List<String>,
)