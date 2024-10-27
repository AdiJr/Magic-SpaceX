package com.adi.magicspacex.models.launch

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LaunchLinks(
    val flickr: Flickr,
    val patch: Patch,
    val webcast: String,
)